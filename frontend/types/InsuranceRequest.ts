export interface InsuranceRequestDTO {
    postalCode: string
    vehicleType: string
    annualMileage: number
}

export interface InsuranceResponseDTO {
    id: string
    postalCode: string
    vehicleType: string
    annualMileage: number
    calculatedPremium: number
    createdAt: string
}
