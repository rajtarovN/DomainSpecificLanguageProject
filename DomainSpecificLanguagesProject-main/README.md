# DomainSpecificLanguagesProject

Project for Domain Specific Languages course. 

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![made-with-python](https://img.shields.io/badge/Made%20with-Python-1f425f.svg)](https://www.python.org/)


# Introduction

A domain-specific language (DSL) for defining templates for advertisement websites. The project is a part of the DSL (Domain Specific Language) course in the Software Engineering Master's program at the Faculty of Technical Sciences in Novi Sad.

# Description

Our project is designed to help in the process of generating form templates for advertisement websites. By offering a variety of built-in fields, as well as the ability for users to define their custom fields through a template, our system aims to simplify the creation of complex online forms.

The language offers a range of pre-built fields, such as dropdown menus, text boxes, checkboxes, etc... Additionally, our system offers advanced functionality for defining custom fields, allowing users to add their own Jinja2 files and customizable parameters.

The generated output consists of HTML, CSS and JS files which can then be used and run on websites.

# Example

**Custom Jinja2 image template**
```html
<label for="{{ field.type.name }}" {% if field.required %}class="field-label required"{% else %}class="field-label" {% endif %}>{{ field.type.label }}</label>
<div class="section">
    <div class="upload__box">
        <div class="upload__btn-box">
          <label class="upload__btn">
            <p>Upload images</p>
            <input type="file" multiple="" data-max_length="20" class="upload__inputfile" onclick="ImgUpload()" {% if field.required %}required{% endif %}>
          </label>
        </div>
        <div class="upload__img-wrap"></div>
    </div>
</div>
```

**Advertisement template example (example.add)**

```cpp
templates {
  template ImagesTemplate "images_template.j2"
}
advertisement AdvertisementName {
    title: "Example Advertisement"
    advertisement_info {
      submit_url: "https://en96sm60rk9gv.x.pipedream.net"
      success_message: "Your submission was successful!"
      redirect_url: "http://example.com/thankyou"
      error_message: "There was an error submitting your form."
    }
    item_section ItemImages {
        field item_images {
            type: ImagesTemplate i1
            label: "Images of the item"
        }
    }
    item_section ItemDetails {
        @Required
        field item_name {
            type: text t1
            label: "Item Name"
            placeholder: "Enter Item Name"
        }
        @Required
        field item_condition {
            type: radio r1
            label: "Item Condition"
            options [
              option (label: "New", value: "new")
              option (label: "Used", value: "used")
            ]
        }
        @Required
        field item_description {
            type: multiline m1
            label: "Item Description"
            placeholder: "Write a short description of the item"
        }
    }
    item_section PaymentDetails {
        @Required
        field item_price {
            type: number n1
            label: "Item Price"
            placeholder: "Enter Item Price"
        }
        @Required
        field delivery_options {
            type: radio r2
            label: "Delivery Options"
            options [
              option (label: "Post", value: "post")
              option (label: "Other", value: "Other")
              option (label: "None", value: "none")
            ]
        }

        field additional_notes {
            type: text m2
            label: "Additional Notes"
            placeholder: "Notes regarding delivery, price or discounts"
        }
        field discount_code {
            type: checkbox c1
            label: "Accepts Discount Code"
        }
    }
    item_section ContactInfo {
        @Required
        field contact_name {
            type: text t2
            label: "Contact Name"
            placeholder: "Enter Name And Surname"
        }
        @Required
        field contact_email {
            type: email e2
            label: "Contact Email"
            placeholder: "Enter Email"
        }
        field contact_number {
            type: tel tel1
            label: "Contact Number"
            placeholder: "Enter Phone Number"
        }
        field contact_address {
            type: text t3
            label: "Contact Address"
            placeholder: "Enter Street Address"
        }
    }
}
```

**Generated template**

![output](https://github.com/RajnovicTeodora/DomainSpecificLanguagesProject/blob/feature/requirements_and_readme/dsl_project/test/example.png)

# VS Code support
 
## Snippets configuration

Copy the DomainSpecificLanguagesProject/snippets/custom_dsl_snippets.code-snippets file to the AppData/Roaming/Code/User/snippets folder.

## Syntax highlight configuration

Copy the whole dsl directory into the ~.vscode\extensions. folder.

# Run instructions

1. Install [python >= 3.9](https://www.python.org/downloads/)
2. Create and activate a virtual environment 
3. Install requirements: 
```
$ pip install -r requirements.txt
```
4. Execute setup.py:
```
python install .
```
5. Position yourself in the test directory
6. Generate a form template from the example.add file:
```
$ textX generate example.add --target html+css+js
```
Optionally you can enter the ```-o``` or ```--output-path``` and enter the output folder, otherwise, it will be set to the input file location.

The resulting files will be placed in the output folder if the example is valid. 

# Contributors

- [Rajtarov Nataša](https://github.com/rajtarovN)
- [Mirilović Olivera](https://github.com/oljamirilovic)
- [Rajnović Teodora](https://github.com/RajnovicTeodora)
- [Rakić Marko](https://github.com/RemaxX7)