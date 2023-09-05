export const useStatusStore = defineStore('statuses', () => {
    const statuses = ref([]);

    const setStatuses = (inputStatuses) => {
        statuses.value = inputStatuses
    }

    return {statuses, setStatuses}
})