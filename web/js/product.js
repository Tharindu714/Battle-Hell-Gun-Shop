var modelList;
var stockList;
var boreList;

async function loadFeatures() {

    const response = await fetch("LoadFeatures");

    if (response.ok) {
        const json = await response.json();
        const BrandList = json.BrandList; //BRAND LIST
        modelList = json.modelList; //MODEL LIST
        const magList = json.magList; //MAGAZINE LIST
        stockList = json.stockList; //STOCK LIST
        const muzzList = json.muzzList; //MUZZLE TYPE LIST
        boreList = json.boreList; //BARREL TYPE LIST
        const actionList = json.actionList; //ACTION LIST
        const btypeList = json.btypeList; //BULLET TYPE LIST
        const conditionList = json.conditionList; //CONDITION LIST
        const personList = json.personList; //PERSON TYPE LIST


        loadSelect("categorySelect", BrandList, ["id", "name"]);
        //loadSelect("modelSelect", modelList, ["id", "name"]);
        loadSelect("magSelect", magList, ["id", "name"]);
        //loadSelect("stockSelect", stockList, ["id", "w_trigger"]);
        loadSelect("muzzSelect", muzzList, ["id", "name"]);
        //loadSelect("barrelSelect", boreList, ["id", "chamber"]);
        loadSelect("actionSelect", actionList, ["id", "bolt"]);
        loadSelect("btypeSelect", btypeList, ["id", "type"]);
        loadSelect("conditionSelect", conditionList, ["id", "name"]);
        loadSelect("personSelect", personList, ["id", "type"]);

    } else {
        Swal.fire({
            title: "Please try again Later ",
            text: json.content,
            icon: "error"
        });
    }

}

function loadSelect(id, list, props) {
    const selectTag = document.getElementById(id);
    list.forEach(item => {
        let tag = document.createElement("option");
        tag.value = item[props[0]];
        tag.innerHTML = item[props[1]];
        selectTag.appendChild(tag);
    });
}

function updateModels() {

    let modelTag = document.getElementById("modelSelect");
    let selectedCategoryId = document.getElementById("categorySelect").value;
    modelTag.length = 1; //REMOVE ALL VALUES AND KEEP ONLY 1st VALUE

    modelList.forEach(model => {
        if (model.brand.id == selectedCategoryId) { //ADD MODELS IN SELECTED CATEGORY
            let tag = document.createElement("option");
            tag.value = model.id;
            tag.innerHTML = model.name;
            modelTag.appendChild(tag);
        }
    });
}

function updateStock() {

    let stockTag = document.getElementById("stockSelect");
    let selectedstockId = document.getElementById("magSelect").value;
    stockTag.length = 1; //REMOVE ALL VALUES AND KEEP ONLY 1st VALUE

    stockList.forEach(stock => {
        if (stock.magazine.id == selectedstockId) { //ADD MODELS IN SELECTED CATEGORY
            let tag = document.createElement("option");
            tag.value = stock.id;
            tag.innerHTML = stock.w_trigger;
            stockTag.appendChild(tag);
        }
    });
}

function updateBore() {

    let boreTag = document.getElementById("barrelSelect");
    let selectedboreId = document.getElementById("muzzSelect").value;
    boreTag.length = 1; //REMOVE ALL VALUES AND KEEP ONLY 1st VALUE

    boreList.forEach(barrel => {
        if (barrel.muzzle.id == selectedboreId) { //ADD MODELS IN SELECTED CATEGORY
            let tag = document.createElement("option");
            tag.value = barrel.id;
            tag.innerHTML = barrel.chamber;
            boreTag.appendChild(tag);
        }
    });
}

async function productListing() {

    //INPUTS
    const titleTag = document.getElementById("title");
    const descriptionTag = document.getElementById("description");
    const priceTag = document.getElementById("price");
    const qtyTag = document.getElementById("qty");

    //SELECTS
    const categorySelectTag = document.getElementById("categorySelect");
    const modelSelectTag = document.getElementById("modelSelect");

    const magSelectTag = document.getElementById("magSelect");
    const stockSelectTag = document.getElementById("stockSelect");

    const muzzSelectTag = document.getElementById("muzzSelect");
    const barrelSelectTag = document.getElementById("barrelSelect");

    const actionSelectTag = document.getElementById("actionSelect");

    const btypeSelectTag = document.getElementById("btypeSelect");

    const conditionSelectTag = document.getElementById("conditionSelect");

    const personSelectTag = document.getElementById("personSelect");

    //PRODUCT IMAGES
    const img1Tag = document.getElementById("img1");
    const img2Tag = document.getElementById("img2");
    const img3Tag = document.getElementById("img3");
    const img4Tag = document.getElementById("img4");

    //SHOW MSG
    const msgTag = document.getElementById("message");

    //FROM DATA
    const data = new FormData();
    data.append("title", titleTag.value);
    data.append("description", descriptionTag.value);
    data.append("price", priceTag.value);
    data.append("qty", qtyTag.value);

    data.append("categoryId", categorySelectTag.value);
    data.append("modelId", modelSelectTag.value);

    data.append("magId", magSelectTag.value);
    data.append("stockId", stockSelectTag.value);

    data.append("muzzleId", muzzSelectTag.value);
    data.append("boreId", barrelSelectTag.value);

    data.append("actionID", actionSelectTag.value);

    data.append("bulletTypeId", btypeSelectTag.value);

    data.append("conditionId", conditionSelectTag.value);

    data.append("personId", personSelectTag.value);

    data.append("img1", img1Tag.files[0]);
    data.append("img2", img2Tag.files[0]);
    data.append("img3", img3Tag.files[0]);
    data.append("img4", img4Tag.files[0]);


    //OPEN REQUEST
    const response = await fetch(
            "ProductListing",
            {
                method: "POST",
                body: data
            }
    );
    //HANDLE RESPONSE
    if (response.ok) {

        const json = await response.json();
        if (json.success) {

            titleTag.value = "";
            descriptionTag.value = "";
            priceTag.value = 0.00;
            qtyTag.value = 1;

            categorySelectTag.value = 0;
            modelSelectTag.length = 1;

            magSelectTag.value = 0;
            stockSelectTag.value = 1;

            muzzSelectTag.value = 0;
            barrelSelectTag.value = 1;

            actionSelectTag.value = 0;
            btypeSelectTag.value = 0;

            conditionSelectTag.value = 0;
            personSelectTag.value = 0;

            img1Tag.value = null;
            img2Tag.value = null;
            img3Tag.value = null;
            img4Tag.value = null;

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


