<template>
    <section id="ipMaskChart">
        <div class="chart-content">
            <net-chart></net-chart>
            <el-button class="add-button" size="medium"><i class="fa fa-plus"></i></el-button>
        </div>
        <loader></loader>
    </section>
</template>

<script>
    import NetChart from "../components/NetChart";
    import Loader   from "../components/loader"
    export default {
        data: () => {
            return {

            }
        },
        components: {
            'net-chart': NetChart,
            'loader': Loader
        }
    }
</script>

<style scoped>
    #ipMaskChart {
    }
    .chart-content {
        text-align: center;
        padding: 20px;
    }
    .add-button {
        display: inline-block;
        width: 100%;
        font-size: 21px;
    }
</style>