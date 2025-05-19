import {defineStore} from 'pinia'
import {useNotifications} from '~/composables/useNotifications'

interface VehicleFactorDTO {
    vehicleType: string
    factor: number
}

export const useVehicleStore = defineStore('vehicle', () => {
    const vehicleTypes = ref<{ label: string; value: string }[]>([])
    const {handleError} = useNotifications()

    const loadFactors = async (): Promise<void> => {
        try {
            const config = useRuntimeConfig()
            const result = await $fetch<VehicleFactorDTO[]>(`${config.public.apiBase}/vehicle-factors`)
            vehicleTypes.value = result.map(v => ({
                label: `${capitalize(v.vehicleType)}`,
                value: v.vehicleType
            }))
        } catch (err: any) {
            handleError('Error loading vehicle factors', err)
        }
    }

    const capitalize = (value: string): string =>
        value.charAt(0).toUpperCase() + value.slice(1).toLowerCase()

    return {vehicleTypes, loadFactors}
})
