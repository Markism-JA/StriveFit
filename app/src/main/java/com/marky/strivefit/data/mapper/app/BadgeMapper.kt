package com.marky.strivefit.data.mapper.app

import com.google.firebase.Timestamp
import com.marky.strivefit.data.local.entities.app.BadgeEntity
import com.marky.strivefit.data.remote.dto.app.BadgeDto

fun BadgeEntity.toDto(): BadgeDto {
    return BadgeDto(
        id = this.id,
        badgeName = this.badgeName,
        description = this.description,
        designConcept = this.designConcept,
        keywords = this.keywords,
        iconLink = this.iconLink,
        createdAt = this.createdAt?.let { Timestamp(it) },
        updatedAt = this.updatedAt?.let { Timestamp(it) }
    )
}

fun List<BadgeEntity>.toDtoList(): List<BadgeDto> {
    return this.map { it.toDto() }
}

fun BadgeDto.toEntity(): BadgeEntity? {
    val entityId = this.id ?: return null

    return BadgeEntity(
        id = entityId,
        badgeName = this.badgeName,
        description = this.description,
        designConcept = this.designConcept,
        keywords = this.keywords,
        iconLink = this.iconLink,
        createdAt = this.createdAt?.toDate(),
        updatedAt = this.updatedAt?.toDate()
    )
}

fun List<BadgeDto>.toEntityList(): List<BadgeEntity> {
    return this.mapNotNull { dto ->
        dto.toEntity()
    }
}