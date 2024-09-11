
var modelList;
async function loadFeatures() {

    const response = await fetch(
            "LoadFeatures"
            );

    if (response.ok) {
        const json = await response.json();

        const categoryList = json.categoryList;
        modelList = json.modelList;
        const colorList = json.colorList;
        const storageList = json.storageList;
        const conditionList = json.conditionList;

        loadSelect("categoryselect", categoryList, ["id", "name"]);
        loadSelect("modelselect", modelList, ["id", "name"]);
        loadSelect("colorselect", colorList, ["id", "name"]);
        loadSelect("storageselect", storageList, ["id", "value"]);
        loadSelect("Conditionselect", conditionList, ["id", "name"]);

        if (json.success) {

        } else {

        }

    } else {
        document.getElementById("message").innerHTML = "Please try again Later ";
    }
}

function loadSelect(selectTagId, list, propertyArray) {
    const SelectTag = document.getElementById(selectTagId);
    list.forEach(item => {
        let optionTag = document.createElement("option");
        optionTag.value = item[propertyArray[0]];
        optionTag.innerHTML = item[propertyArray[1]];
        SelectTag.appendChild(optionTag);
    });

}

function updateModels() {
    let ModelTag = document.getElementById("modelselect");
    ModelTag.length = 1; //Remove all (ModelTag.length ="0";)

    let SelectedcategoryId = document.getElementById("categoryselect").value;

    modelList.forEach(model => {
        if (model.category.id == SelectedcategoryId) {
            let optionTag = document.createElement("option");
            optionTag.value = model.id;
            optionTag.innerHTML = model.name;
            ModelTag.appendChild(optionTag);
        }
    });
}

async function productListing() {
    const Category_SelectTag = document.getElementById("categoryselect");
    const Model_SelectTag = document.getElementById("modelselect");
    const title_Tag = document.getElementById("title");
    const description_Tag = document.getElementById("description");
    const Storage_SelectTag = document.getElementById("storageselect");
    const Color_SelectTag = document.getElementById("colorselect");
    const Condition_SelectTag = document.getElementById("Conditionselect");
    const price_Tag = document.getElementById("price");
    const QTY_Tage = document.getElementById("qty");
    const Img1_Tag = document.getElementById("image1");
    const Img2_Tag = document.getElementById("image2");
    const Img3_Tag = document.getElementById("image3");

    const data = new FormData();
    data.append("categoryId", Category_SelectTag.value);
    data.append("modelId", Model_SelectTag.value);
    data.append("title", title_Tag.value);
    data.append("description", description_Tag.value);
    data.append("storageId", Storage_SelectTag.value);
    data.append("colorId", Color_SelectTag.value);
    data.append("conditionId", Condition_SelectTag.value);
    data.append("price", price_Tag.value);
    data.append("quantity", QTY_Tage.value);

    data.append("image1", Img1_Tag.files[0]);
    data.append("image2", Img2_Tag.files[0]);
    data.append("image3", Img3_Tag.files[0]);

    const response = await fetch(
            "ProductListing",
            {
                method: "POST",
                body: data
            }
    );
    if (response.ok) {
        const json = await response.json();
        if (json.success) {

            Category_SelectTag.value = 0;
            Model_SelectTag.length = 1;
            title_Tag.value = "";
            description_Tag.value = "";
            Storage_SelectTag.value = 0;
            Color_SelectTag.value = 0;
            Condition_SelectTag.value = 0;
            price_Tag.value = "";
            QTY_Tage.value = 1;
            Img1_Tag.value = null;
            Img2_Tag.value = null;
            Img3_Tag.value = null;

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

