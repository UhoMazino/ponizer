package ru.mazino.ponizer

import kotlinx.serialization.Serializable


@Serializable
data class DeviceDTO(
    val id: Int,
    val name: String,
    val serial: String,
    private val device_type: DeviceModelDTO,
    private val comments: String,
    private val custom_fields: DeviceCustomFieldsDTO,
    private val primary_ip: DeviceIpDTO
) {
    val ip: String
        get() = primary_ip.address.substringBefore("/")
    val vendor: String
        get() = device_type.manufacturer.name
    val model: String
        get() = device_type.model
    val community: String
        get() = custom_fields.`SNMP community`.label
    val softwareVersion: String
        get() = comments.split(Regex("\r\n")).get(0).substringAfter(":").trim()
    val hardwareVesion: String
        get() = comments.split(Regex("\r\n")).get(1).substringAfter(":").trim()
    val MAC: String
        get() = comments.split(Regex("\r\n")).get(2).substringAfter(":").trim()

}

@Serializable
data class DeviceListDTO(val results: List<DeviceDTO>)

@Serializable
data class DeviceModelDTO(val model: String, val manufacturer: DeviceVendorDTO)

@Serializable
data class DeviceVendorDTO(val name: String)

@Serializable
data class DeviceCustomFieldsDTO(
    val District: String?, //TODO Actually not used, but after removing parser breaked
    val `SNMP community`: DeviceCommunityDTO
)

@Serializable
data class DeviceCommunityDTO(val label: String)

@Serializable
data class DeviceIpDTO(val address: String)

