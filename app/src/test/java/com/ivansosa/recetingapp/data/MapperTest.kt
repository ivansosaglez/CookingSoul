package com.ivansosa.recetingapp.data

import com.ivansosa.recetingapp.data.network.dto.MealDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun `MealDto toDomain correctly maps ingredients and measures`() {
        val dto = MealDto(
            idMeal = "1",
            strMeal = "Test Meal",
            strMealThumb = null,
            strCategory = "Beef",
            strArea = "British",
            strInstructions = "Cook it.",
            strTags = null,
            strYoutube = null,
            strIngredient1 = "Beef", strMeasure1 = "1kg",
            strIngredient2 = "Salt", strMeasure2 = "1 tsp",
            strIngredient3 = "", strMeasure3 = "", // Empty
            strIngredient4 = null, strMeasure4 = null, // Null
            strIngredient5 = null, strIngredient6 = null, strIngredient7 = null, strIngredient8 = null,
            strIngredient9 = null, strIngredient10 = null, strIngredient11 = null, strIngredient12 = null,
            strIngredient13 = null, strIngredient14 = null, strIngredient15 = null, strIngredient16 = null,
            strIngredient17 = null, strIngredient18 = null, strIngredient19 = null, strIngredient20 = null,
            strMeasure5 = null, strMeasure6 = null, strMeasure7 = null, strMeasure8 = null,
            strMeasure9 = null, strMeasure10 = null, strMeasure11 = null, strMeasure12 = null,
            strMeasure13 = null, strMeasure14 = null, strMeasure15 = null, strMeasure16 = null,
            strMeasure17 = null, strMeasure18 = null, strMeasure19 = null, strMeasure20 = null
        )

        val domain = dto.toDomain()

        assertEquals("Test Meal", domain.name)
        assertEquals(2, domain.ingredients.size)
        assertEquals("Beef", domain.ingredients[0].ingredient)
        assertEquals("1kg", domain.ingredients[0].measure)
        assertEquals("Salt", domain.ingredients[1].ingredient)
        assertEquals("1 tsp", domain.ingredients[1].measure)
    }
}
