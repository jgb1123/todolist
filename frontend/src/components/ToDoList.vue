<!-- <template>
  <div id="todolist">
    <q-card>
      <q-card-section horizontal style="width: 840px">
        <q-separator vertical />
        <q-card-section style="width: 200px;"  class="bg-white">
          <div class="text-h5"> Date </div>
        </q-card-section>
        <q-separator vertical />
        <q-card-section style="width: 250px;" class="bg-white">
          <div class="text-h5"> Title </div>
        </q-card-section>
        <q-separator vertical />
        <q-card-section style="width: 400px;" class="bg-white">
          <div class="text-h5"> Content </div>
        </q-card-section>
        <q-separator vertical />
        <q-card-section style="width: 200px;" class="bg-white">
          <div class="text-h5"> Status</div>
        </q-card-section>
      </q-card-section>
    </q-card>

    <q-card v-for= "(item, i) in items" :key="i" class="q-mt-md bg-white text-black" style="width: 900px;">
      <q-card-section horizontal>
        <q-card-section style="width: 200px;"  class="bg-light-blue-4">
          <div class="text-h5">{{ dayjs(item.targetTime).format('YYYY-MM-DD HH:mm') }}</div>
        </q-card-section>
        <q-separator vertical />
        <q-card-section style="width: 250px;" class="bg-light-blue-3">
          <div class="text-h6">{{ item.title }}</div>
        </q-card-section>
        <q-separator vertical />
        <q-card-section style="width: 400px;" class="bg-light-blue-2">
          <div>
            <q-scroll-area style="height: 50px; max-width: 400px; word-break: break-all">
              <div class="q-py-xs">{{ item.content }}</div>
            </q-scroll-area>
          </div>
        </q-card-section>
        <q-separator vertical />
        <q-card-section style="width: 200px;" class="bg-light-blue-1">
          <div class="text-h6">{{ item.statusName }}</div>
        </q-card-section>
        <q-separator vertical />
        <q-card-actions align="right" style="width: 60px;">
          <q-btn icon="mode_edit" color="primary" @click="editItem(item)" />
          <q-btn icon="delete" color="primary" @click="confirm(item.itemId)" />
        </q-card-actions>
      </q-card-section>
    </q-card>
  </div>
</template> -->
<template>
  <div class="q-pa-md">
    <q-table
      class="my-sticky-header-table"
      flat bordered
      :rows="items"
      :columns="columns"
      row-key="name"
      :hide-pagination="true"
      :pagination.sync="pagination"
    >
      <template v-slot:body-cell-actions="props">
        <q-td :props="props">
          <q-btn icon="mode_edit" color="primary" @click="editItem(props.row)" />
          <q-btn icon="delete" color="primary" @click="confirm(props.row.itemId)" />
        </q-td>
      </template>
    </q-table>
  </div>
</template>



<script setup>
import axios from '../utils/axios.js';
import dayjs from "dayjs";

const router = useRouter();
const $q = useQuasar();
// const page = ref(1);
const records = ref(0);
const items = ref([]);
const emit = defineEmits(['change-edit-pop-up'])

const pagination = {
  page: 1,
  rowsPerPage: 0
}
const columns = [
  {
    name: 'date',
    required: true,
    label: 'Date',
    align: 'left',
    field: 'targetTime',
    format: val => `${dayjs(val).format('YYYY-MM-DD HH:mm')}`,
    sortable: true
  },
  {
    name: 'title',
    required: true,
    label: 'title',
    align: 'left',
    field: 'title',
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'content',
    required: true,
    label: 'content',
    align: 'left',
    field: 'content',
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'status',
    required: true,
    label: 'status',
    align: 'left',
    field: 'statusName',
    format: val => `${val}`,
    sortable: true
  },
  {
    name: 'actions',
    align: 'center',
    label: 'Action'
  }
]

const getItem = async () => {
  const res = await axios.get('/item/find')

  if(res.status === 200) {
    records.value = res.headers['x-total-count'] || 0;
    items.value = res.data.data;
  }
}

const confirm = (itemId) => {
  $q.dialog({
    ok: {
      color: 'negative'
    },
    cancel: true,
    message: '해당 일정을 정말 삭제하시겠습니까?',
  }).onOk(() => {
    deleteItem(itemId)
  })
}

const alertDelete = () => {
  $q.dialog({
    message: '삭제되었습니다.'
  })
}

const deleteItem = async (itemId) => {
  const res = await axios.post(`/item/delete/${itemId}`)
  if(res.status === 204){
    alertDelete()
    await getItem()
  }
}

const editItem = async (item) => {
  await emit(`change-edit-pop-up`, item)
}

defineExpose({getItem})

onMounted( ()=> {
  getItem();
})
</script>

<style lang="sass">
.my-sticky-header-table
  height: 700px

  .q-table__top,
  .q-table__bottom,
  thead tr:first-child th
    background-color: #B8EBFF

  thead tr th
    position: sticky
    z-index: 1
  thead tr:first-child th
    top: 0

  &.q-table--loading thead tr:last-child th
    top: 10px

  tbody
    scroll-margin-top: 48px
</style>