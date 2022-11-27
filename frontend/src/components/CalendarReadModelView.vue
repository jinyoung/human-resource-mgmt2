<template>

    <v-data-table
        :headers="headers"
        :items="calendarReadModel"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'CalendarReadModelView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "userId", value: "userId" },
                { text: "yearAndMonth", value: "yearAndMonth" },
                { text: "scheduleData", value: "scheduleData" },
            ],
            calendarReadModel : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/calendarReadModels'))

            temp.data._embedded.calendarReadModels.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.calendarReadModel = temp.data._embedded.calendarReadModels;
        },
        methods: {
        }
    }
</script>

