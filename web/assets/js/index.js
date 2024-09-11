async function CheckSignin() {
    const response = await fetch(
            "CheckSignin"
            );
    if (response.ok) {
        const json = await response.json();

        const response_DTO = json.response_DTO;

        if (response_DTO.success) {
            const user = response_DTO.content;

            let quickLink_custom = document.getElementById("quick-link-custom");

            let quickLink1 = document.getElementById("stQuick1");
            quickLink1.remove();

            let quickLink2 = document.getElementById("stQuick2");
            quickLink2.remove();

            let New_liTag = document.createElement("li");
            let New_liTag1 = document.createElement("a");
            New_liTag1.href = "#";
            New_liTag.innerHTML = user.first_name + " " + user.last_name;
            quickLink_custom.appendChild(New_liTag);
            quickLink_custom.appendChild(New_liTag1);

            let login_btn = document.getElementById("st_login");
            login_btn.href = "signOut";
            login_btn.innerHTML = "Sign Out";

            let st_div = document.getElementById("st-div");
            st_div.remove();

        } else {
            console.log("Not Sign in");
        }

        const productList = json.products;
        let i = 1;
        productList.forEach(product => {
            document.getElementById("st-title-" + i).innerHTML = product.title;
            document.getElementById("st-link-" + i).href = "single-product.html?id=" + product.id;
            document.getElementById("st-image-" + i).src = "Product_Images/New_SmartTrade_Product_" + product.id + "/New_SmartTrade_Product_" + product.id + "_1.png";
            document.getElementById("st-price-" + i).innerHTML = new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(product.price);
            i++;
        });

        $('.slider-content-activation-one').slick({
            infinite: true,
            slidesToShow: 1,
            slidesToScroll: 1,
            arrows: false,
            dots: false,
            focusOnSelect: false,
            speed: 500,
            fade: true,
            autoplay: true,
            asNavFor: '.slider-thumb-activation-one',
        });

        $('.slider-thumb-activation-one').slick({
            infinite: true,
            slidesToShow: 2,
            slidesToScroll: 1,
            arrows: false,
            dots: true,
            focusOnSelect: false,
            speed: 1000,
            autoplay: true,
            asNavFor: '.slider-content-activation-one',
            prevArrow: '<button class="slide-arrow prev-arrow"><i class="fal fa-long-arrow-left"></i></button>',
            nextArrow: '<button class="slide-arrow next-arrow"><i class="fal fa-long-arrow-right"></i></button>',
            responsive: [{
                    breakpoint: 991,
                    settings: {
                        slidesToShow: 1,
                    }
                }
            ]

        });
    }
}

async function viewCart() {
    const response = await fetch("cart.html");
    if (response.ok) {
        const cartHTMLText = await response.text();
        const Parser = new DOMParser();
        const cartHTML = Parser.parseFromString(cartHTMLText, "text/html");

        const Cart_main = cartHTML.querySelector(".main-wrapper");
        console.log(Cart_main);

        document.querySelector(".main-wrapper").innerHTML = Cart_main.innerHTML;
        loadCartItems();

    }
}



