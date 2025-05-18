// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    nitro: {
        prerender: {
            crawlLinks: false
        }
    },
    ssr: false,
    modules: [],
    typescript: {
        strict: true
    },
    css: [
        "primeicons/primeicons.css",
        "primeflex/primeflex.css",
        'primevue/resources/primevue.css',
        'primevue/resources/themes/lara-light-blue/theme.css',
        'primevue/resources/themes/saga-blue/theme.css',
        'normalize.css/normalize.css'
    ],
    compatibilityDate: '2025-05-15',
    devtools: {enabled: true},
    runtimeConfig: {
        public: {
            apiBase: process.env.API_BASE_URL || 'https://localhost:8443/api'
        }
    }

})
