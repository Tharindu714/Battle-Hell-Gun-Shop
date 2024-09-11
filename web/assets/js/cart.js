async function loadCartItems() {
    const response = await fetch(
            "LoadCartItem"
            );

    if (response.ok) {
        const json = await response.json();
        if (json.length == 0) {
            Swal.fire({
                title: "Your Cart is Empty",
                text: json.content,
                icon: "error"
            });
            //window.location = "index.html";
        } else {
            let cartItemContainer = document.getElementById("cart-item-container");
            let cartItemRow = document.getElementById("cart-item-row");
            
                        let totalQty = 0;
            let total = 0;
            
            cartItemContainer.innerHTML = "";
            
            json.forEach(item => {
                let itemSub_total = item.product.price * item.qty;
                
                total += itemSub_total;
                totalQty += item.qty;

                let CartItemRowClone = cartItemRow.cloneNode(true);
                CartItemRowClone.querySelector("#cart-item-a").href = "single-product.html?id=" + item.id;
                CartItemRowClone.querySelector("#cart-item-img").src = "Product_Images/New_SmartTrade_Product_" + item.product.id + "/New_SmartTrade_Product_" + item.product.id + "_1.png";
                CartItemRowClone.querySelector("#cart-item-title").innerHTML = item.product.title;
                CartItemRowClone.querySelector("#cart-item-price").innerHTML = "Rs. " + new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(item.product.price);
                CartItemRowClone.querySelector("#cart-item-qty").value = item.qty;
                CartItemRowClone.querySelector("#cart-item-subTotal").innerHTML = "Rs. " + new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format((itemSub_total));
                cartItemContainer.appendChild(CartItemRowClone);
            });
            document.getElementById("cart-total-qty").innerHTML = totalQty +" units";
            document.getElementById("cart-total").innerHTML = "Rs. " + new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format((total));
        }
    } else {
        Swal.fire({
            title: "Unable to process your Request",
            text: json.content,
            icon: "error"
        });
    }
}


