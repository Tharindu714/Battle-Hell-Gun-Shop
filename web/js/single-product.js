async function loadProduct() {

    const parameters = new URLSearchParams(window.location.search);

    if (parameters.has("id")) {
        const productId = parameters.get("id");

        const response = await fetch("LoadSingleProduct?id=" + productId);

        if (response.ok) {

            const json = await response.json();
            console.log(json.product);

            const id = json.product.id;
            document.getElementById("img1").src = "product-images/" + id + "/image1.png";
            document.getElementById("img2").src = "product-images/" + id + "/image2.png";
            document.getElementById("img3").src = "product-images/" + id + "/image3.png";
            document.getElementById("img4").src = "product-images/" + id + "/image4.png";


            document.getElementById("img1-thumb").src = "product-images/" + id + "/image1.png";
            document.getElementById("img2-thumb").src = "product-images/" + id + "/image2.png";
            document.getElementById("img3-thumb").src = "product-images/" + id + "/image3.png";
            document.getElementById("img4-thumb").src = "product-images/" + id + "/image4.png";


            document.getElementById("product-title").innerHTML = json.product.title;
            document.getElementById("product-title1").innerHTML = json.product.title;
            document.getElementById("product-published-on").innerHTML = json.product.reg_date;
            document.getElementById("product-price").innerHTML = new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(json.product.price);

            document.getElementById("product-category").innerHTML = json.product.model.brand.name;
            document.getElementById("product-model").innerHTML = json.product.model.name;

            document.getElementById("product-trigger").innerHTML = json.product.stock.w_trigger;
            document.getElementById("product-mag").innerHTML = json.product.stock.magazine.name;
            document.getElementById("product-forestock").innerHTML = json.product.stock.forestock.name;

            document.getElementById("product-action").innerHTML = json.product.action.bolt;
            document.getElementById("product-safe").innerHTML = json.product.action.safetyclip;

            document.getElementById("product-chamber").innerHTML = json.product.barrel.chamber;
            document.getElementById("product-sight").innerHTML = json.product.barrel.gun_sight.name;
            document.getElementById("product-muzzle").innerHTML = json.product.barrel.muzzle.name;

            document.getElementById("product-person").innerHTML = json.product.person.type;
            document.getElementById("product-condition").innerHTML = json.product.gun_condition.name;

            document.getElementById("product-qty").innerHTML = json.product.qty;


            document.getElementById("product-description").innerHTML = json.product.description;
            document.getElementById("product-description1").innerHTML = json.product.description;


            //ADD EVENT LISTENER 
            document.getElementById("add-to-cart-main")
                    .addEventListener(
                            "click",
                            (e) => {

                        addToCart(id, document.getElementById("add-to-cart-qty").value);
                        e.preventDefault();
                    });

            document.getElementById("add-to-wishlist-main")
                    .addEventListener(
                            "click",
                            (e) => {

                        addToWishList(id);
                        e.preventDefault();
                    });

            //GET SIMILER PRODUCT
            let ProductHtml = document.getElementById("similer-product");

            //CLAER CONTAINER
            document.getElementById("similer-product-main").innerHTML = "";

            json.productList.forEach(item => {
                let productCloneHtml = ProductHtml.cloneNode(true);


                //CLONE
                productCloneHtml.querySelector("#similer-product-img").src = "product-images/" + item.id + "/image1.png";
                productCloneHtml.querySelector("#similer-product-a1").href = "single-product.html?id=" + item.id;
//                productCloneHtml.querySelector("#similer-product-a2").href = "single-product.html?id=" + item.id;
                productCloneHtml.querySelector("#similer-product-title").innerHTML = item.title;
                productCloneHtml.querySelector("#similer-product-person").innerHTML = item.person.type;
                productCloneHtml.querySelector("#similer-product-price").innerHTML = "Rs. " + new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(item.price);
                productCloneHtml.querySelector("#similer-product-add-to-cart")
                        .addEventListener(
                                "click",
                                (e) => {
                            addToCart(item.id, 1);
                            e.preventDefault();
                        });

                productCloneHtml.querySelector("#similer-product-add-to-wishlist")
                        .addEventListener(
                                "click",
                                (e) => {
                            addToWishList(item.id);
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
            title: "Please try again Later ",
            text: json.content,
            icon: "error"
        });
    }

}

async function addToWishList(id) {

    const response = await fetch(
            "addToWishList?id=" + id
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
