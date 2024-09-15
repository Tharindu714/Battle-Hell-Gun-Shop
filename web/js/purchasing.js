async function loadPurchasedItems() {

    const response = await fetch(
            "loadPurchasedItems"
            );

    if (response.ok) {

        const json = await response.json();

        if (json.length == 0) {
            Swal.fire({
                title: "Your Purchasing History is Empty",
                text: json.content,
                icon: "error"
            });

        } else {

            console.log(json);

            let container = document.getElementById("order-item-container");
            let row = document.getElementById("order-item-row");

            let totalQty = 0;
            let total = 0;

            container.innerHTML = "";

            json.forEach(item => {
                let itemSubTotal = item.product.price * item.qty;

                totalQty += item.qty;
                total += itemSubTotal;

                let RowClone = row.cloneNode(true);
                if (item.product) {
                    RowClone.querySelector("#order-item-a").href = "single-product.html?id=" + item.product.id;
                    RowClone.querySelector("#order-item-img").src = "product-images/" + item.product.id + "/image1.png";
                    RowClone.querySelector("#order-item-title").innerHTML = item.product.title;
                    RowClone.querySelector("#order-item-status").innerHTML = item.purchase_status.name;
                    RowClone.querySelector("#order-item-price").innerHTML = new Intl.NumberFormat(
                            "en-US",
                            {
                                minimumFractionDigits: 2
                            }
                    ).format(item.product.price);
                    RowClone.querySelector("#order-item-qty").value = item.qty;
                    RowClone.querySelector("#order-item-subtotal").innerHTML = new Intl.NumberFormat(
                            "en-US",
                            {
                                minimumFractionDigits: 2
                            }
                    ).format(itemSubTotal);
                    container.appendChild(RowClone);
                    loadCusDetails();
                } else {
                    console.error("Product Data is missing");
                }

            });
        }

    } else {
        Swal.fire({
            title: "Unable to process your Request",
            text: json.content,
            icon: "error"
        });
    }

}

async function loadCusDetails() {
    const response = await fetch(
            "loadCusDetails"
            );

    if (response.ok) {
        const json = await response.json();
        console.log(json);
        let detail_container = document.getElementById("detail-order-item-container");
        let detail_row = document.getElementById("detail-order-item-row");
        let detail_row2 = document.getElementById("detail-order-item-row2");
        detail_container.innerHTML = "";
        let detail_RowClone = detail_row.cloneNode(true);
        let detail_RowClone2 = detail_row2.cloneNode(true);

        json.forEach(item => {
            detail_RowClone.querySelector("#order-cus-fullname").innerHTML = item.user.first_name + " " +
                    item.user.last_name;
        });
        detail_container.appendChild(detail_RowClone);

        json.forEach(itemA => {
            detail_RowClone2.querySelector("#order-address").innerHTML = itemA.address.line1 + ", " +
                    itemA.address.line2 + ", " +
                    itemA.address.city.name + " | " +
                    itemA.address.postal_code;
        });
        detail_container.appendChild(detail_RowClone2);

    } else {
        Swal.fire({
            title: "Unable to process your Request",
            text: json.content,
            icon: "error"
        });
    }
}

