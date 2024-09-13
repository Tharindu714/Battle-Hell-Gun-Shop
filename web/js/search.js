async function loadData() {
    const response = await fetch("LoadData");


    if (response.ok) {
        const json = await response.json();
        console.log(json);

        loadOption("category", json.categoryList, "name");
        loadOption("condition", json.conditionList, "name");
        loadOption("action", json.actionList, "bolt");
        loadOption("barrel", json.barrelList, "chamber");
        loadOption("stock", json.stockList, "w_trigger");
        loadOption("person", json.personList, "type");
        updateProductView(json);

    } else {
        Swal.fire({
            title: "Something went Wrong",
            text: json.content,
            icon: "error"
        });
    }

}

function loadOption(prefix, dataList, property) {
    let options = document.getElementById(prefix + "-options");
    let li = document.getElementById(prefix + "-li");
    options.innerHTML = "";

    dataList.forEach(data => {
        let li_clone = li.cloneNode(true);

        if (prefix == "color") {
            li_clone.style.borderColor = data[property];
            li_clone.querySelector("#" + prefix + "-a").style.backgroundColor = data[property];
        } else {
            li_clone.querySelector("#" + prefix + "-a").innerHTML = data[property];
        }

        options.appendChild(li_clone);
    });

    //from template js
    const all_li = document.querySelectorAll("#" + prefix + "-options li");
    all_li.forEach(x => {
        x.addEventListener('click', function () {
            all_li.forEach(y => y.classList.remove('chosen'));
            this.classList.add('chosen');
        });
    });
}

async function searchProducts(firstResult) {

    //get search data
    let category_name = document.getElementById("category-options")
            .querySelector(".chosen")?.querySelector("a").innerHTML;

    let condition = document.getElementById("condition-options")
            .querySelector(".chosen")?.querySelector("a").innerHTML;

    let action = document.getElementById("action-options")
            .querySelector(".chosen")?.querySelector("a").innerHTML;

    let barrel = document.getElementById("barrel-options")
            .querySelector(".chosen")?.querySelector("a").innerHTML;

    let stock = document.getElementById("stock-options")
            .querySelector(".chosen")?.querySelector("a").innerHTML;

    let person = document.getElementById("person-options")
            .querySelector(".chosen")?.querySelector("a").innerHTML;

    let price_range_start = $("#slider-range").slider('values', 0);
    let price_range_end = $("#slider-range").slider('values', 1);

    let sort_text = document.getElementById("st-sort").value;

    const data = {
        firstResult: firstResult,
        category_name: category_name,
        condition: condition,
        action: action,
        barrel: barrel,
        stock: stock,
        person: person,
        price_range_start: price_range_start,
        price_range_end: price_range_end,
        sort_text: sort_text
    };

    const response = await fetch("SearchProducts",
            {
                method: "POST",
                body: JSON.stringify(data),
                headers: {
                    "Content-Type": "application/json"
                }
            });

    if (response.ok) {
        const json = await response.json();
        console.log(json);
        updateProductView(json);
        //currentPage = 0;
    } else {
        Swal.fire({
            title: "Something went Wrong",
            icon: "error"
        });
    }
}

var st_product = document.getElementById("st-product");
var st_pagination_button = document.getElementById("st-pagination-button");

var currentPage = 0;

function updateProductView(json) {

    let st_product_container = document.getElementById("st-product-container");

    st_product_container.innerHTML = "";

    json.productList.forEach(product => {
        let st_product_clone = st_product.cloneNode(true);

        //update cards
        st_product_clone.querySelector("#st-product-a-1").href = "single-product.html?id=" + product.id;
        st_product_clone.querySelector("#st-product-img-1").src = "product-images/" + product.id + "/image1.png";
        st_product_clone.querySelector("#st-product-a-2").href = "single-product.html?id=" + product.id;
        st_product_clone.querySelector("#st-product-title-1").innerHTML = product.title;
        st_product_clone.querySelector("#st-product-price-1").innerHTML = new Intl.NumberFormat(
                "en-US",
                {
                    minimumFractionDigits: 2
                }
        ).format(product.price);

        st_product_container.appendChild(st_product_clone);

    });

    //start pagination
    let st_pagination_container = document.getElementById("st-pagination-container");
    st_pagination_container.innerHTML = "";

    let product_count = json.allProductCount;
    const product_per_page = 9;

    let pages = Math.ceil(product_count / product_per_page);

    //add previous button
    if (currentPage != 0) {
        let st_pagination_button_clone_prev = st_pagination_button.cloneNode(true);
        st_pagination_button_clone_prev.innerHTML = "Prev";

        st_pagination_button_clone_prev.addEventListener("click", e => {
            currentPage--;
            searchProducts(currentPage * 9);
        });

        st_pagination_container.appendChild(st_pagination_button_clone_prev);
    }

    //add page buttons
    for (let i = 0; i < pages; i++) {
        let st_pagination_button_clone = st_pagination_button.cloneNode(true);
        st_pagination_button_clone.innerHTML = i + 1;

        st_pagination_button_clone.addEventListener("click", e => {
            currentPage = i;
            searchProducts(i * 9);
        });

        if (i === currentPage) {
            st_pagination_button_clone.className = "primary-btn text-light fw-bold me-4";
        } else {
            st_pagination_button_clone.className = "primary-btn text-light fw-bold me-4";
        }

        st_pagination_container.appendChild(st_pagination_button_clone);
    }

    //add Next button
    if (currentPage != (pages - 1)) {
        let st_pagination_button_clone_next = st_pagination_button.cloneNode(true);
        st_pagination_button_clone_next.innerHTML = "Next";

        st_pagination_button_clone_next.addEventListener("click", e => {
            currentPage++;
            searchProducts(currentPage * 9);
        });

        st_pagination_container.appendChild(st_pagination_button_clone_next);
    }

}

function reload(){
    window.location.reload();
}