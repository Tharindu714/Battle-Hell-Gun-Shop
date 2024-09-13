async function loadProfileData() {

    const response = await fetch(
            "CheckSignIn"
            );
    if (response.ok) {
        const json = await response.json();
        console.log(json);
        const response_dto = json.response_dto;
        if (response_dto.success) {

            const user = response_dto.content;
            console.log(user);

            let user_input = document.getElementById("user-detail-input");

            let profile_first_name_input = document.getElementById("profile-first-name");
            profile_first_name_input.remove();

            let new_in_tag1 = document.createElement("li");
            new_in_tag1.innerHTML = "First Name : " + user.first_name;
            user_input.appendChild(new_in_tag1);

            let profile_last_name_input = document.getElementById("profile-last-name");
            profile_last_name_input.remove();

            let new_in_tag2 = document.createElement("li");
            new_in_tag2.innerHTML = "Last Name : " + user.last_name;
            user_input.appendChild(new_in_tag2);

            let profile_email_input = document.getElementById("profile-email");
            profile_email_input.remove();

            let new_in_tag3 = document.createElement("li");
            new_in_tag3.innerHTML = "Email Address : " + user.email;
            user_input.appendChild(new_in_tag3);
        } else {
            Swal.fire({
                title: "User is not valid",
                text: json.content,
                icon: "error"
            });
        }
        loadCity();
    } else {
        Swal.fire({
            title: "Please try again Later ",
            text: json.content,
            icon: "error"
        });
    }
}

async function loadCity() {

    const response = await fetch("LoadCheckout");

    if (response.ok) {
        const json = await response.json();
        console.log(json);

        if (json.success) {
            //store response data
            const address = json.address;
            const cityList = json.cityList;
            const cartList = json.cartList;

            //load cities
            let citySelect = document.getElementById("profile-city");
            citySelect.length = 1;

            cityList.forEach(city => {
                let cityOption = document.createElement("option");
                cityOption.value = city.id;
                cityOption.innerHTML = city.name;
                citySelect.appendChild(cityOption);
            });
        }
    }
}

async function SubmitProfile() {

    //get address data
    let city = document.getElementById("profile-city");
    let address1 = document.getElementById("profile-address1");
    let address2 = document.getElementById("profile-address2");
    let postalCode = document.getElementById("profile-postal-code");
    let mobile = document.getElementById("profile-mobile");
    const data = {
        cityId: city.value,
        address1: address1.value,
        address2: address2.value,
        postalCode: postalCode.value,
        mobile: mobile.value
    };
    const response = await fetch("UpdateProfile", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    });
    if (response.ok) {
        const json = await response.json();
        console.log(json);
        if (json.success) {
            Swal.fire({
                position: "center",
                icon: "success",
                title: json.content,
                showConfirmButton: false,
                timer: 1500
            });

            city.value = 0;
            address1.value = "";
            address2.value = "";
            postalCode.value = "";
            mobile.value ="";
           
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


