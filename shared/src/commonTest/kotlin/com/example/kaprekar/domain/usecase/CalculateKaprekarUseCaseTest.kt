package com.example.kaprekar.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CalculateKaprekarUseCaseTest {

    private val useCase = CalculateKaprekarUseCase()

    @Test
    fun validateInput_returnsSuccess_forValid4DigitNumbersWithMultipleDigits() {
        val validNumbers = listOf("6825", "3524", "1000", "0123", "9876")
        for (num in validNumbers) {
            val result = useCase.validateInput(num)
            assertTrue(result is ValidationResult.Success, "Expected $num to be valid")
        }
    }

    @Test
    fun validateInput_returnsError_forUniformNumbers() {
        val uniformNumbers = listOf("1111", "0000", "9999", "7777")
        for (num in uniformNumbers) {
            val result = useCase.validateInput(num)
            assertTrue(result is ValidationResult.Error, "Expected $num to fail validation")
        }
    }

    @Test
    fun validateInput_returnsError_forInvalidLengthsOrNonNumeric() {
        val invalidInputs = listOf("123", "12345", "abcd", "12a4", "", "  ")
        for (num in invalidInputs) {
            val result = useCase.validateInput(num)
            assertTrue(result is ValidationResult.Error, "Expected '$num' to fail validation")
        }
    }

    @Test
    fun execute_reachesKaprekarConstant_forInput3524In3Steps() {
        val steps = useCase.execute("3524")
        assertEquals(3, steps.size)
        
        // Step 1: 5432 - 2345 = 3087
        assertEquals(1, steps[0].stepNumber)
        assertEquals("5432", steps[0].descending)
        assertEquals("2345", steps[0].ascending)
        assertEquals(3087, steps[0].resultValue)
        assertEquals("3087", steps[0].resultString)
        assertFalse(steps[0].isKaprekarConstant)

        // Step 2: 8730 - 0378 = 8352
        assertEquals(2, steps[1].stepNumber)
        assertEquals("8730", steps[1].descending)
        assertEquals("0378", steps[1].ascending)
        assertEquals(8352, steps[1].resultValue)

        // Step 3: 8532 - 2358 = 6174
        assertEquals(3, steps[2].stepNumber)
        assertEquals("8532", steps[2].descending)
        assertEquals("2358", steps[2].ascending)
        assertEquals(6174, steps[2].resultValue)
        assertTrue(steps[2].isKaprekarConstant)
    }

    @Test
    fun execute_handlesLeadingZeroPaddingCorrectly() {
        // Input 1000:
        // Step 1: 1000 - 0001 = 0999
        // Step 2: 9990 - 0999 = 8991
        val steps = useCase.execute("1000")
        assertEquals("1000", steps[0].descending)
        assertEquals("0001", steps[0].ascending)
        assertEquals(999, steps[0].resultValue)
        assertEquals("0999", steps[0].resultString)

        assertEquals("9990", steps[1].descending)
        assertEquals("0999", steps[1].ascending)
        assertEquals(8991, steps[1].resultValue)
    }

    @Test
    fun execute_throwsException_whenCalledWithInvalidInput() {
        assertFailsWith<IllegalArgumentException> {
            useCase.execute("1111")
        }
    }
}
