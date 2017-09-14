$(document).ready(function () {

    function getCount(data, weight) {
        var count = 0;
        for (var i = 0; i < data.length; ++i) {
            if (data[i] === weight) {
                count++;
                addWeight('left', weight);
                addWeight('right', weight);
            }
        }
        return count;
    }

    function addWeight(side, weight) {
        var barbell = document.getElementById('barbell');
        var plate = document.createElement('div');

        plate.style.position = 'relative';
        plate.style.width = '20px';
        plate.style.height = '50px';
        plate.style.backgroundColor = 'black';
        if (side === 'left') {
            plate.style.left = '46%';
            plate.style.top = '170px';
        } else if (side === 'right') {
            plate.style.left = '71%';
            plate.style.top = '125px';
        }

        plate.style.color = 'white';
        plate.style.textAlign = 'center';

        plate.innerHTML = weight;

        console.log(plate);

        barbell.appendChild(plate);
    }

    $("#calculateTargetWeightButton").click(function () {
        var targetWeightText = $("#targetWeightText").val();

        $.post("http://localhost:8080/weightLifting/weightCalc",
            {
                weight: targetWeightText
            },
            function (data, status) {
                data = JSON.parse(data);

                var count45 = 0;
                for (var i = 0; i < data.length; ++i) {
                    if (data[i] === 45) {
                        count45++;
                    }
                }

                $("#lbPlates45").text(getCount(data, 45));
                $("#lbPlates25").text(getCount(data, 25));
                $("#lbPlates10").text(getCount(data, 10));
                $("#lbPlates5").text(getCount(data, 5));
                $("#lbPlates2point5").text(getCount(data, 2.5));
            });
    });
});