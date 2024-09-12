async function checkSignIn() {

    const response = await fetch(
            "CheckSignIn"
            );
    if (response.ok) {

        const json = await response.json();
        console.log(json);
        const response_dto = json.response_dto;
        if (response_dto.success) {
//SIGNED IN
            const user = response_dto.content;
            console.log(user);
            
            let st_quick_link = document.getElementById("st-quick-link");
            
            let st_quick_link_li_1 = document.getElementById("st-quick-link-li-1");
            st_quick_link_li_1.remove();
            
            let st_quick_link_li_2 = document.getElementById("st-quick-link-li-2");
            st_quick_link_li_2.remove();
            
            let new_li_tag1 = document.createElement("li");
            new_li_tag1.innerHTML = user.first_name + " " + user.last_name;
            new_li_tag1.href = "Profile.html";
            
            st_quick_link.appendChild(new_li_tag1);
            
            let st_btn_1 = document.getElementById("st-btn-1");
            st_btn_1.href = "SignOut";
            st_btn_1.innerHTML = "SignOut";
        } else {
//NOT SIGNED IN 
            console.log("Not Signed In");
        }


        const productList = json.products;
        let i = 1;
        productList.forEach(product => {
            document.getElementById("homepage-product-title-" + i).innerHTML = product.title;
            document.getElementById("homepage-product-a-" + i).href = "single-product.html?id=" + product.id;
            document.getElementById("homepage-img-" + i).src = "product-images/" + product.id + "/image1.png";
            document.getElementById("homepage-product-price-" + i).innerHTML = new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(product.price);
            i++;
            document.getElementById("home-add-to-cart-1").addEventListener("click", (e) => {
                addToCart(product.id, 1);
                e.preventDefault();
            });
            document.getElementById("home-add-to-cart-2").addEventListener("click", (e) => {
                addToCart(product.id, 1);
                e.preventDefault();
            });
            document.getElementById("home-add-to-cart-3").addEventListener("click", (e) => {
                addToCart(product.id, 1);
                e.preventDefault();
            });
            document.getElementById("home-add-to-cart-4").addEventListener("click", (e) => {
                addToCart(product.id, 1);
                e.preventDefault();
            });
            document.getElementById("home-add-to-cart-5").addEventListener("click", (e) => {
                addToCart(product.id, 1);
                e.preventDefault();
            });
            document.getElementById("home-add-to-cart-6").addEventListener("click", (e) => {
                addToCart(product.id, 1);
                e.preventDefault();
            });
            document.getElementById("home-add-to-cart-7").addEventListener("click", (e) => {
                addToCart(product.id, 1);
                e.preventDefault();
            });
            document.getElementById("home-add-to-cart-8").addEventListener("click", (e) => {
                addToCart(product.id, 1);
                e.preventDefault();
            });
        });
    }
}

async function addToCart(id, qty) {

    const response = await fetch(
            "AddToCart?id=" + id + "&qty=" + qty
            );
    if (response.ok) {

        const json = await response.json();
        if (json.success) {
            Swal.fire({
                position: "center",
                icon: "success",
                title: json.content,
                showConfirmButton: false,
                timer: 1500
            });
        } else {
            Swal.fire({
                title: "Something went Wrong",
                text: json.content,
                icon: "error"
            });
        }
    } else {
        Swal.fire({
            title: "Please try again Later ",
            text: json.content,
            icon: "error"
        });
    }

}
