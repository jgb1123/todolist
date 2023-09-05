export const useItemStore = defineStore('items', () => {
    const items = ref([]);

    const setItems = (inputItems) => {
        items.value = inputItems
    }

    return {items, setItems}
})