var mealAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

// http://api.jquery.com/jQuery.ajax/#using-converters
$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            var json = JSON.parse(stringData);
            $(json).each(function () {
                // console.log(this);
                this.dateTime = this.dateTime.replace('T', ' ').substr(0, 16);
            });
            return json;
        }
    }
});

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (!data.excess) {
                    $(row).attr("data-mealExcess", false);
                } else {
                    $(row).attr("data-mealExcess", true);
                }
            }
        }),
        updateTable: function () {
            $.get(mealAjaxUrl, updateTableByData);
        }
    }
    );

});
// $(function () {
//     makeEditable({
//         ajaxUrl: mealAjaxUrl,
//         datatableApi: $("#datatable").DataTable({
//             "paging": false,
//             "info": true,
//             "columns": [
//                 {
//                     "data": "dateTime"
//                 },
//                 {
//                     "data": "description"
//                 },
//                 {
//                     "data": "calories"
//                 },
//                 {
//                     "defaultContent": "Edit",
//                     "orderable": false
//                 },
//                 {
//                     "defaultContent": "Delete",
//                     "orderable": false
//                 }
//             ],
//             "order": [
//                 [
//                     0,
//                     "desc"
//                 ]
//             ]
//         }),
//         updateTable: updateFilteredTable
//     });
// });