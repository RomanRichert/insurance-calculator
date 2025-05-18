import PrimeVue from 'primevue/config'
import ToastService from 'primevue/toastservice'
import DialogService from 'primevue/dialogservice'
import Tooltip from 'primevue/tooltip'
import {defineNuxtPlugin} from '#app'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import Slider from 'primevue/slider'
import Toast from 'primevue/toast'
import Card from 'primevue/card'
import Dropdown from 'primevue/dropdown'
import InputNumber from 'primevue/inputnumber'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'

export default defineNuxtPlugin((nuxtApp) => {
    nuxtApp.vueApp.use(PrimeVue, {ripple: true})
    nuxtApp.vueApp.use(ToastService)
    nuxtApp.vueApp.use(DialogService)

    nuxtApp.vueApp.component('Button', Button)
    nuxtApp.vueApp.component('InputText', InputText)
    nuxtApp.vueApp.component('Dropdown', Dropdown)
    nuxtApp.vueApp.component('Slider', Slider)
    nuxtApp.vueApp.component('Toast', Toast)
    nuxtApp.vueApp.component('Card', Card)
    nuxtApp.vueApp.component('DataTable', DataTable)
    nuxtApp.vueApp.component('Column', Column)
    nuxtApp.vueApp.component('InputNumber', InputNumber)
    nuxtApp.vueApp.directive('tooltip', Tooltip)
})
