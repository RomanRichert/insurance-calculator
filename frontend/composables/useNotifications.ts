import {useToast} from 'primevue/usetoast'

export const useNotifications = () => {
    const toast = useToast()

    const handleError = (userMessage: string, exception: any): void => {
        console.error(userMessage, exception)

        const backendMessage = exception?.response?._data?.message || 'Unexpected server response'

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

    return {handleError, handleSuccess}
}
