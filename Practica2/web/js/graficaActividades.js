Highcharts.chart('containerExp', {
    chart: {
        type: 'bar'
    },
    title: {
        text: 'Estadisticas de actividades en las experiencias existentes',
        align: 'left'
    },
    xAxis: {
        type: 'category',
        labels: {
            style: {
                fontSize: '24px'
            }
        }
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Experiencias'
        }
    },
    legend: {
        reversed: true
    },
    plotOptions: {
        series: {
            stacking: 'normal',
            dataLabels: {
                enabled: true
            }
        }
    },
    series: [{
            name: "Cantidad de actividades",
            data: datosExp
    }]
});
