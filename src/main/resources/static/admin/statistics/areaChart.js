 // pieChart
 $(document).ready(function () {
     $.ajax({
         type: 'GET',
         dataType: "json",
         contentType: "application/json",
         url: 'http://localhost:8080/rest/orders/inventoryByMonth',

         success: function (result) {
             google.charts.load('current', {
                 'packages': ['corechart']
             });
             google.charts.setOnLoadCallback(function () {
                 drawChart(result);
             })
         }
     });

     function drawChart(result) {
         var data = new google.visualization.DataTable();
         data.addColumn('number', "Name");
         data.addColumn('number', "Total");
         var dataArray = [];
         $.each(result, function (i, obj) {
             dataArray.push([obj.month, obj.sum])
         });

         data.addRows(dataArray);
         var options = {
             title: 'Company Performance',
             hAxis: {
                 title: 'Month',
                 titleTextStyle: {
                     color: '#333'
                 }
             },
             vAxis: {
                 minValue: 0
             }
         };

         var areachart = new google.visualization.AreaChart(document.getElementById('areachart_div'));
         areachart.draw(data, options);
     }
 })