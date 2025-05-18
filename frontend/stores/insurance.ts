import {defineStore} from 'pinia'
import type {InsuranceRequestDTO, InsuranceResponseDTO} from '~/types/InsuranceRequest'
import {useToast} from "primevue/usetoast";

export const useInsuranceStore = defineStore('insurance', () => {
    const isLoading = ref(false)
    const result = ref<InsuranceResponseDTO | null>(null)
    const error = ref<string | null>(null)
    const toast = useToast()
    const config = useRuntimeConfig()
    const entries = ref<InsuranceResponseDTO[]>([])

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
        error.value = null
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

    const handleError = (userMessage: string, exception: any): void => {
        console.error(userMessage, exception)

        const backendMessage = exception?.response?._data?.message || 'Unexpected server response'
        error.value = backendMessage

        toast.add({
            severity: 'error',
            summary: 'Error',
            detail: `${userMessage}: ${backendMessage}`,
            life: 5000
        })
    }

    const handleSuccess = (message: string): void => {
        toast.add({
            severity: 'success',
            summary: 'Success',
            detail: message,
            life: 3000
        })
    }

    return {result, isLoading, calculate, loadAll, entries}
})
