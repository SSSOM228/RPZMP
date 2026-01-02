package com.example.myexpensetracker.data.mappers

import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.localdb.entities.CategoryEntity

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = this.id,
        name = this.name,
        iconName = this.iconName,
        colorHex = this.colorHex,
        type = enumValueOf(this.type),
        isArchived = this.isArchived,
    )
}

fun Category.toEntity(): CategoryEntity{
    return CategoryEntity(
        id = this.id,
        name = this.name,
        iconName = this.iconName,
        colorHex = this.colorHex,
        type = this.type.name,
        isArchived = this.isArchived,
    )
}