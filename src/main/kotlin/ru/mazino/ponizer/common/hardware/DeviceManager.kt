package ru.mazino.ponizer.common.hardware

import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.mazino.ponizer.DeviceFactory
import ru.mazino.ponizer.DeviceListDTO
import ru.mazino.ponizer.api.hardware.Device

@Component
class DeviceManager(
    @Value("\${app.deviceListBaseURL}") private val baseURL: String,
    @Value("\${app.token}") private val token: String,
    @Autowired val ctx: ApplicationContext
) {
    lateinit var devices: List<Device>

    fun init() {
        val factory = DeviceFactory(ctx)
        devices = RestTemplate().exchange(
            UriComponentsBuilder.fromHttpUrl(baseURL).run {
                path("/dcim/devices")
                queryParam("role", "OLT")
                queryParam("limit", "3")
                queryParam("manufaturer", "RAISECOM", "GATERAY")
                queryParam("model", "iscom5508", "gr-ep-olt1-8")
                toUriString()
            },
            HttpMethod.GET,
            HttpEntity<Unit>(HttpHeaders().apply { set("Authorization", token) }),
            DeviceListDTO::class.java,
        ).body?.results!!.map {
            return@map factory.createInstance(it)
        }
    }
}