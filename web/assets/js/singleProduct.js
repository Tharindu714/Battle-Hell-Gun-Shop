async function loadProduct() {
    const parameter = new URLSearchParams(window.location.search);
    if (parameter.has("id")) {
        const ProductId = parameter.get("id");
        const response = await fetch("Load_SingleProduct?id=" + ProductId);
        if (response.ok) {
            const json = await response.json();
            const id = json.product.id;
            document.getElementById("image1").src = "Product_Images/New_SmartTrade_Product_" + id + "/New_SmartTrade_Product_" + id + "_1.png";
            document.getElementById("image2").src = "Product_Images/New_SmartTrade_Product_" + id + "/New_SmartTrade_Product_" + id + "_2.png";
            document.getElementById("image3").src = "Product_Images/New_SmartTrade_Product_" + id + "/New_SmartTrade_Product_" + id + "_3.png";

            document.getElementById("image1-thumb").src = "Product_Images/New_SmartTrade_Product_" + id + "/New_SmartTrade_Product_" + id + "_1.png";
            document.getElementById("image2-thumb").src = "Product_Images/New_SmartTrade_Product_" + id + "/New_SmartTrade_Product_" + id + "_2.png";
            document.getElementById("image3-thumb").src = "Product_Images/New_SmartTrade_Product_" + id + "/New_SmartTrade_Product_" + id + "_3.png";

            document.getElementById("image1-quick").src = "Product_Images/New_SmartTrade_Product_" + id + "/New_SmartTrade_Product_" + id + "_1.png";
            document.getElementById("image2-quick").src = "Product_Images/New_SmartTrade_Product_" + id + "/New_SmartTrade_Product_" + id + "_2.png";
            document.getElementById("image3-quick").src = "Product_Images/New_SmartTrade_Product_" + id + "/New_SmartTrade_Product_" + id + "_3.png";

            document.getElementById("product-title").innerHTML = json.product.title;
            document.getElementById("product-published").innerHTML = json.product.date_time;
            document.getElementById("product-price").innerHTML = new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(json.product.price);
            document.getElementById("product-category").innerHTML = json.product.model.category.name;
            document.getElementById("product-model").innerHTML = json.product.model.name;
            document.getElementById("product-condition").innerHTML = json.product.product_condition.name;
            document.getElementById("product-qty").innerHTML = json.product.qty;
            document.getElementById("color-border").style.borderColor = json.product.color.name;
            document.getElementById("color-background").style.backgroundColor = json.product.color.name;
            document.getElementById("product-storage").innerHTML = json.product.storage.value;
            document.getElementById("product-desc").innerHTML = json.product.description;
            document.getElementById("add-to-cart-main").addEventListener(
                    "click",
                    (e) => {
                addToCart(
                        json.product.id,
                        document.getElementById("add-to-cart-qty").value
                        );
                e.preventDefault();
            });

            let ProductHtml = document.getElementById("similer-product");
            document.getElementById("similer-product-main").innerHTML = "";

            json.productList.forEach(item => {
                let productCloneHtml = ProductHtml.cloneNode(true);

                productCloneHtml.querySelector("#similer-product-img").src = "Product_Images/New_SmartTrade_Product_" + item.id + "/New_SmartTrade_Product_" + item.id + "_1.png";
                productCloneHtml.querySelector("#similer-product-a1").href = "single-product.html?id=" + item.id;
                productCloneHtml.querySelector("#similer-product-a2").href = "single-product.html?id=" + item.id;
                productCloneHtml.querySelector("#similer-product-title").innerHTML = item.title;
                productCloneHtml.querySelector("#similer-product-storage").innerHTML = item.storage.value;
                productCloneHtml.querySelector("#similer-product-price").innerHTML = "Rs. " + new Intl.NumberFormat(
                        "en-US",
                        { 
                            minimumFractionDigits: 2
                        }
                ).format(item.price);
                productCloneHtml.querySelector("#similer-product-color-border").style.borderColor = item.color.name;
                productCloneHtml.querySelector("#similer-product-color").style.backgroundColor = item.color.name;
                productCloneHtml.querySelector("#similer-product-add-to-cart").addEventListener(
                        "click",
                        (e) => {
                    addToCart(item.id);
                    e.preventDefault();
                });
                document.getElementById("similer-product-main").appendChild(productCloneHtml);
            });
            $('.recent-product-activation').slick({
                infinite: true,
                slidesToShow: 4,
                slidesToScroll: 4,
                arrows: true,
                dots: false,
                prevArrow: '<button class="slide-arrow prev-arrow"><i class="fal fa-long-arrow-left"></i></button>',
                nextArrow: '<button class="slide-arrow next-arrow"><i class="fal fa-long-arrow-right"></i></button>',
                responsive: [{
                        breakpoint: 1199,
                        settings: {
                            slidesToShow: 3,
                            slidesToScroll: 3
                        }
                    },
                    {
                        breakpoint: 991,
                        settings: {
                            slidesToShow: 2,
                            slidesToScroll: 2
                        }
                    },
                    {
                        breakpoint: 479,
                        settings: {
                            slidesToShow: 1,
                            slidesToScroll: 1
                        }
                    }
                ]
            });
        } else {
            window.location = "index.html";
        }
    } else {
        window.location = "index.html";
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
            title: "Unable to process your Request",
            text: json.content,
            icon: "error"
        });
    }
}


