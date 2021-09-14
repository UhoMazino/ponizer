package ru.mazino.ponizer.api.protocol

import org.snmp4j.smi.OID

interface Session {
    fun get(oid: String): String
    fun get(oid: OID): String
    fun getAll(oids: Array<String>): Array<String>
    fun getAll(oids: Array<OID>): Array<String>
    fun walk(oid: String): Map<String, String>
    fun walk(oid: OID): Map<String, String>

}