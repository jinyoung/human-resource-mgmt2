<template>

    <v-data-table
        :headers="headers"
        :items="vacationStatus"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'VacationStatusView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "startDate", value: "startDate" },
                { text: "endDate", value: "endDate" },
                { text: "reason", value: "reason" },
                { text: "status", value: "status" },
            ],
            vacationStatus : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/vacationStatuses'))

            temp.data._embedded.vacationStatuses.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.vacationStatus = temp.data._embedded.vacationStatuses;
        },
        methods: {
        }
    }
</script>

