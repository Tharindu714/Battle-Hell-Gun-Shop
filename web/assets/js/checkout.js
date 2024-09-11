async function loadData() {
const response = await fetch(
        "LoadCheckout"
        );
        if (response.ok) {
const json = await response.json();
        if (json.success) {

//store response Data
const address = json.address;
        const cityList = json.cityList;
        const cartList = json.cartList;
        //load data to dropdown
        let citySelect = document.getElementById("st-city");
        citySelect.length = 1;
        cityList.forEach(city => {
        let cityOption = document.createElement("option");
                cityOption.value = city.id;
                cityOption.innerHTML = city.name;
                citySelect.appendChild(cityOption);
        });
        //load current address
        let cuurentAddressCheckBox = document.getElementById("checkbox1");
        cuurentAddressCheckBox.addEventListener("change", e => {

        let first_name = document.getElementById("first-name");
                let last_name = document.getElementById("last-name");
                let city = document.getElementById("st-city");
                let address1 = document.getElementById("address1");
                let address2 = document.getElementById("address2");
                let postal_code = document.getElementById("postal-code");
                let mobile = document.getElementById("mobile");
                if (cuurentAddressCheckBox.checked) {
        first_name.value = address.first_name;
                last_name.value = address.last_name;
                city.value = address.city.id;
                city.disabled = true;
                city.dispatchEvent(new Event("change"));
                address1.value = address.line1;
                address2.value = address.line2;
                postal_code.value = address.postal_code;
                mobile.value = address.mobile;
        } else {
        first_name.value = "";
                last_name.value = "";
                city.value = 0;
                city.disabled = false;
                city.dispatchEvent(new Event("change"));
                address1.value = "";
                address2.value = "";
                postal_code.value = "";
                mobile.value = "";
        }
        });
        let st_tbody = document.createElement("st-tbody");
        let st_item_tr = document.createElement("st-item-tr");
        let st_item_subtotal_tr = document.createElement("st-item-subtotal-tr");
        let st_item_shipping_tr = document.createElement("st-item-shipping-tr");
        let st_item_total_tr = document.createElement("st-item-total-tr");
        st_tbody.innerHTML = "";
        let sub_total = 0;
        //load Cart Items
        cartList.forEach(item => {
        let st_item_clone = st_item_tr.cloneNode(true);
                st_item_clone.querySelector("#st-item-tile").innerHTML = item.product.title;
                st_item_clone.querySelector("#st-item-qty").innerHTML = item.qty;
                let item_subTotal = item.product.price * item.qty;
                sub_total += item_subTotal;
                st_item_clone.querySelector("#st-item-subtotal").innerHTML = new Intl.NumberFormat(
                "en-US",
        {
        minimumFractionDigits: 2
        }
        ).format(item_subTotal);
                st_tbody.appendChild(st_item_clone);
        });
        st_item_subtotal_tr.querySelector("#st-subtotal").innerHTML = new Intl.NumberFormat(
        "en-US",
        {
        minimumFractionDigits: 2
                }
).format(sub_total);
        st_tbody.appendChild(st_item_subtotal_tr);
        citySelect.addEventListener("change", e => {
        //update Shipping Charges
        let item_count = cartList.length;
                //check Colombo or not (City Check)
                let shipping_amount = 0;
                if (citySelect.value == 1) {
        shipping_amount = item_count * 1000;
        } else {
        shipping_amount = item_count * 2500;
        }
        st_item_shipping_tr.querySelector("#st-shipping-amount").innerHTML = new Intl.NumberFormat(
                "en-US",
        {
        minimumFractionDigits: 2
        }
        ).format(shipping_amount);
                st_tbody.appendChild(st_item_shipping_tr);
                //update total
                let total = sub_total + shipping_amount;
                st_item_shipping_tr.querySelector("#st-total").innerHTML = new Intl.NumberFormat(
                "en-US",
        {
        minimumFractionDigits: 2
        }
        ).format(total);
                st_tbody.appendChild(st_item_total_tr);
        });
        city.dispatchEvent(new Event("change"));
        } else {
window.location = "sign-in.html";
        }
} else {
Swal.fire({
title: "Unable to Process Request",
        text: json.content,
        icon: "error"
        });
        }
}

async function Checkout() {
let isCurrentAddressChecked = document.getElementById("checkbox1").checked;
        //get address data
        let first_name = document.getElementById("first-name");
        let last_name = document.getElementById("last-name");
        let city = document.getElementById("st-city");
        let address1 = document.getElementById("address1");
        let address2 = document.getElementById("address2");
        let postal_code = document.getElementById("postal-code");
        let mobile = document.getElementById("mobile");
        const request_data = {
        isCurrentAddressChecked: isCurrentAddressChecked,
                first_name: first_name.value,
                last_name: last_name.value,
                city: city.value,
                address1: address1.value,
                address2: address2.value,
                postal_code: postal_code.value,
                mobile: mobile.value,
        };
        const response = await fetch(
                "checkout"

        );
        if (response.ok) {
const json = await response.json();
        } else {
Swal.fire({
title: "Unable to Process Request",
        text: json.content,
        icon: "error"
        });
        }
}



