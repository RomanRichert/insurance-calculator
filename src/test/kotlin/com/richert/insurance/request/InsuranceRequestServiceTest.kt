package com.richert.insurance.request

import com.richert.insurance.PostgresTestResource
import com.richert.insurance.PremiumCalculationService
import com.richert.insurance.request.model.InsuranceRequest
import com.richert.insurance.vehicle.VehicleType
import io.quarkus.test.InjectMock
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectSpy
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import java.util.*

@QuarkusTest
@QuarkusTestResource(PostgresTestResource::class)
class InsuranceRequestServiceTest {

    @Inject
    lateinit var service: InsuranceRequestService

    @InjectMock
    lateinit var premiumCalculationService: PremiumCalculationService

    @InjectSpy
    lateinit var insuranceRequestRepository: InsuranceRequestRepository

    @Test
    fun processRequestTest() {
        `when`(premiumCalculationService.calculatePremium("10115", VehicleType.CAR, 15000))
            .thenReturn(180.0)

        val capturedRequests = mutableListOf<InsuranceRequest>()
        doAnswer {
            capturedRequests.add(it.getArgument(0))
        }.`when`(insuranceRequestRepository).persist(any<InsuranceRequest>())

        val result = service.processRequest("10115", VehicleType.CAR, 15000)

        assertEquals("10115", result.postalCode)
        assertEquals(VehicleType.CAR, result.vehicleType)
        assertEquals(15000, result.annualMileage)
        assertEquals(180.0, result.calculatedPremium)
        assertNotNull(result.createdAt)
        assertEquals(1, capturedRequests.size)
    }

    @Test
    fun getByIdFoundTest() {
        val id = UUID.randomUUID()
        val entity = InsuranceRequest().apply { this.id = id }

        `when`(insuranceRequestRepository.findById(id)).thenReturn(entity)

        val result = service.getById(id)
        assertEquals(id, result.id)
    }

    @Test
    fun getByIdNotFoundTest() {
        val id = UUID.randomUUID()
        `when`(insuranceRequestRepository.findById(id)).thenReturn(null)

        val ex = assertThrows<jakarta.ws.rs.NotFoundException> {
            service.getById(id)
        }
        assertTrue(ex.message!!.contains("not found"))
    }

    @Test
    fun getAllPagedTest() {
        val list = listOf(
            InsuranceRequest().apply { id = UUID.randomUUID() },
            InsuranceRequest().apply { id = UUID.randomUUID() }
        )
        `when`(insuranceRequestRepository.findPaged(0, 2)).thenReturn(list)

        val result = service.getAllPaged(0, 2)
        assertEquals(2, result.size)
    }

    @Test
    fun deleteByIdTest() {
        val id = UUID.randomUUID()
        `when`(insuranceRequestRepository.deleteById(id)).thenReturn(true)
        service.deleteById(id)
    }

    @Test
    fun deleteByIdNotFoundTest() {
        val id = UUID.randomUUID()
        `when`(insuranceRequestRepository.deleteById(id)).thenReturn(false)

        val ex = assertThrows<jakarta.ws.rs.NotFoundException> {
            service.deleteById(id)
        }
        assertTrue(ex.message!!.contains("not found"))
    }
}
