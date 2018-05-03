<template>
    <section id="ipMaskChart">
        <div class="chart-content">
            <net-chart></net-chart>
            <el-button class="add-button" size="medium"><i class="fa fa-plus"></i></el-button>
        </div>
    </section>
</template>

<script>
    import NetChart from "../components/NetChart";
    export default {
        data: () => {
            return {

            }
        },
        components: {
            'net-chart': NetChart
        }
    }
</script>

<style>
    #ipMaskChart {
    }
    .chart-content {
        text-align: center;
        padding: 20px 4%;
    }
    .add-button {
        display: inline-block;
        width: 100%;
        font-size: 21px;
    }
</style>