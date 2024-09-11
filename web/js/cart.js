async function loadCartItems() {

    const response = await fetch(
            "LoadCartItems"
            );

    if (response.ok) {

        const json = await response.json();

        if (json.length == 0) {
            Swal.fire({
                title: "Your Cart is Empty",
                text: json.content,
                icon: "error"
            });

        } else {

            console.log(json);

            let container = document.getElementById("cart-item-container");
            let row = document.getElementById("cart-item-row");

            let totalQty = 0;
            let total = 0;

            container.innerHTML = "";

            json.forEach(item => {
                let itemSubTotal = item.product.price * item.qty;

                totalQty += item.qty;
                total += itemSubTotal;

                let RowClone = row.cloneNode(true);
                RowClone.querySelector("#cart-item-a").href = "single-product.html?id=" + item.product.id;
                RowClone.querySelector("#cart-item-img").src = "product-images/" + item.product.id + "/image1.png";
                RowClone.querySelector("#cart-item-title").innerHTML = item.product.title;
                RowClone.querySelector("#cart-item-price").innerHTML = new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(item.product.price);
                RowClone.querySelector("#cart-item-qty").value = item.qty;
                RowClone.querySelector("#cart-item-subtotal").innerHTML = new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(itemSubTotal);

                container.appendChild(RowClone);

            });

            document.getElementById("cart-total-qty").innerHTML = totalQty + " Units";
            document.getElementById("cart-total").innerHTML ="$ "+ new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(total);

        }

    } else {
        Swal.fire({
            title: "Unable to process your Request",
            text: json.content,
            icon: "error"
        });
    }

}