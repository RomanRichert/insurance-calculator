import {defineStore} from 'pinia'
import type {InsuranceRequestDTO, InsuranceResponseDTO} from '~/types/InsuranceRequest'
import {useNotifications} from '~/composables/useNotifications'

export const useInsuranceStore = defineStore('insurance', () => {
    const isLoading = ref(false)
    const result = ref<InsuranceResponseDTO | null>(null)
    const config = useRuntimeConfig()
    const entries = ref<InsuranceResponseDTO[]>([])
    const {handleError, handleSuccess} = useNotifications()

    const loadAll = async (): Promise<void> => {
        const requestUrl = `${config.public.apiBase}/insurance-request`
        const requestMethod = 'GET'
        console.log(`${requestMethod} ${requestUrl}`)

        try {
            entries.value = await $fetch<InsuranceResponseDTO[]>(requestUrl, {method: requestMethod})
        } catch (err: any) {
            handleError('Error loading history', err)
        }
    }

    const calculate = async (request: InsuranceRequestDTO): Promise<void> => {
        isLoading.value = true
        result.value = null
        const requestUrl = `${config.public.apiBase}/insurance-request`
        const requestMethod = 'POST'
        console.log(`${requestMethod} ${requestUrl} ${JSON.stringify(request)}`)

        try {
            const data = await $fetch<InsuranceResponseDTO>(requestUrl, {
                method: requestMethod,
                body: request,
            })
            console.log(`Result: ${JSON.stringify(data)}`)
            handleSuccess('Premium calculated successfully.')
            result.value = data ?? null
            entries.value.unshift(data)
        } catch (err: any) {
            handleError('Error calculating premium', err)
        } finally {
            isLoading.value = false
        }
    }

    return {result, isLoading, calculate, loadAll, entries}
})
