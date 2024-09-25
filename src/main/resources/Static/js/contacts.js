console.log("hii");
const baseUrl="http://localhost:8080";
const viewContactModal=document.getElementById("view_contact_model");


// options with default values
const options = {
  placement: 'bottom-right',
  backdrop: 'dynamic',
  backdropClasses:
      'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
  closable: true,
  onHide: () => {
      console.log('modal is hidden');
  },
  onShow: () => {
      console.log('modal is shown');
  },
  onToggle: () => {
      console.log('modal has been toggled');
  },
};

// instance options object
const instanceOptions = {
id: 'view_contact_model',
override: true
};

const contactModal=new Modal(viewContactModal,options,instanceOptions);

function openContactModal(){
  contactModal.show();
}
function closeContactModal(){
  contactModal.hide();
}

async function loadContactdata(id){
  console.log(id);
  try{
    const data=await(
      await fetch(`${baseUrl}/api/contacts/${id}`)
    ).json();
    console.log(data);
   document.querySelector("#contact_name").innerHTML=data.name;
   document.querySelector("#contact_email").innerHTML=data.email;
   document.querySelector("#contact_phoneNumber").innerHTML=data.phoneNumber;
   document.querySelector("#contact_picture").src=data.picture;
   document.querySelector("#contact_facebook").href=data.facebookLink;
   document.querySelector("#contact_instagram").href=data.instaLink;
   //document.querySelector("#contact_favourite").innerHTML=data.favourite;
   // Assuming you get a `data` object from the API
    if (data.favourite) {
      document.querySelector("#favorite_button").textContent = "Favorites";
    } else {
      document.querySelector("#favorite_button").textContent = "not favorite";
    }

   document.querySelector("#contact_description").innerHTML=data.description;
   document.querySelector("#contact_address").innerHTML=data.address;
   openContactModal();
  }catch(error){
    console.log("Error: ",error);
  }
}

async function deleteContact(id) {

  Swal.fire("working");Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!"
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: "Deleted!",
        text: "Your file has been deleted.",
        icon: "success"
      });
      const url=`${baseUrl}/user/contacts/delete/`+id;

      window.location.replace(url);
    }
  });
  
}