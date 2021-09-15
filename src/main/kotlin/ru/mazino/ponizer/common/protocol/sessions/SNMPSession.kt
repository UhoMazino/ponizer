package ru.mazino.ponizer.common.protocol.sessions

import org.snmp4j.CommunityTarget
import org.snmp4j.PDU
import org.snmp4j.Snmp
import org.snmp4j.mp.SnmpConstants
import org.snmp4j.smi.GenericAddress
import org.snmp4j.smi.OID
import org.snmp4j.smi.OctetString
import org.snmp4j.smi.VariableBinding
import org.snmp4j.transport.DefaultUdpTransportMapping
import org.snmp4j.util.*
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ru.mazino.ponizer.api.protocol.Session
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

@Component
@Scope("prototype")
class SNMPSession(ip: String, community: String) : Session {
    val snmp = Snmp(DefaultUdpTransportMapping().apply { listen() })
    val target = CommunityTarget(GenericAddress.parse("udp:$ip/161"), OctetString(community)).apply {
        retries = 3
        timeout = 5000
        version = SnmpConstants.version2c
    }

    override fun get(oid: String): String {
        return get(OID(oid))
    }

    override fun get(oid: OID): String {
        return snmp.send(PDU().apply { add(VariableBinding(oid)); type = PDU.GET }, target, null).run {
            response.get(0).variable.toString()
        }
    }

    override fun getAll(oids: Array<String>): Array<String> {
        return getAll(oids.map { OID(it) }.toTypedArray())
    }

    override fun getAll(oids: Array<OID>): Array<String> {
        TODO("Not yet implemented")
    }

    override fun walk(oid: String): Map<String, String> {
        return walk(OID(oid))
    }

    override fun walk(oid: OID): Map<String, String> {
        val result = TreeMap<String, String>()
        val treeUtils = TreeUtils(snmp, DefaultPDUFactory())
        val events = treeUtils.getSubtree(target, oid)
        if (events.size == 0 || events == null) return result
        for (event in events) {
            when {
                event == null -> continue
                event.isError -> {
                    println("[${oid}]: ${event.errorMessage}")
                    continue
                }
            }
            val varBinds = event.variableBindings
            if (varBinds.size == 0 || varBinds == null) continue
            for (varBind in varBinds) {
                if (varBind == null) continue
                result[".${varBind.oid}"] = varBind.variable.toString()
            }
        }
        return result
    }

    override fun table(oids: Array<String>): HashMap<String, String> {
        return table(oids.map { oid -> OID(oid) }.toTypedArray())
    }

    override fun table(oids: Array<OID>): HashMap<String, String> {
        val result = HashMap<String, String>()
        val tableUtils = TableUtils(snmp, DefaultPDUFactory())
        val events = tableUtils.getTable(target, oids, null, null)
        for (event in events) {
            if (event.isError) continue
            for (varBind in event.columns) {
                result.put(varBind.oid.toString(), varBind.variable.toString())
            }
        }
        return result
    }


}