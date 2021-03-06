package com.example.yorijori.detail

import com.example.yorijori.data.datasource.remote.api.RecipeDTO

class ExpandableCountryModel {

    companion object {
        const val PARENT = 1
        const val CHILD = 2

    }

    lateinit var countryParent: RecipeDTO.RecipeFinal
    var type: Int = 0
    var isExpanded: Boolean = false
    private var isCloseShown: Boolean = false

    constructor(
        type: Int,
        countryParent: RecipeDTO.RecipeFinal,
        isExpanded: Boolean = false,
        isCloseShown: Boolean = false
    ) {
        this.type = type
        this.countryParent = countryParent
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown
    }
}