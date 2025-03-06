Highcharts.chart('container', {
    chart: {
        type: 'column',
        options3d: {
            enabled: true,
            alpha: 10,
            beta: 25,
            depth: 70
        }
    },
    title: {
        text: 'Estadisticas de experiencias'
    },
    subtitle: {
        text: 'Practica2'
    },
    plotOptions: {
        column: {
            depth: 25
        }
    },
    xAxis: {
        type: 'category',
        labels: {
            skew3d: true,
            style: {
                fontSize: '24px'
            }
        }
    },
    yAxis: {
        title: {
            text: 'Cantidad de experiencias',
            margin: 20
        }
    },
    tooltip: {
        valueSuffix: ' Cantidades'
    },
    series: [{
        name: 'Cantidad de experiencias',
        data: datos
    }]
});
