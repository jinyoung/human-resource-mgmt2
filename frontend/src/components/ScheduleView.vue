<template>

    <v-data-table
        :headers="headers"
        :items="schedule"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'ScheduleView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "userId", value: "userId" },
                { text: "dateId", value: "dateId" },
            ],
            schedule : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/schedules'))

            temp.data._embedded.schedules.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.schedule = temp.data._embedded.schedules;
        },
        methods: {
        }
    }
</script>

