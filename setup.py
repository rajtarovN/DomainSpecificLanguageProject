from setuptools import find_packages, setup

NAME = "dsl_project"
VERSION = "0.1.0"
AUTHOR = "Natasa"
AUTHOR_EMAIL = "rajtarovnatasa@gmail.com"
DESCRIPTION = "Domain Specific languages for defining "
KEYWORDS = "textX DSL textX web-shop form python"
LICENSE = "MIT"
URL = ""

setup(
    name=NAME,
    version=VERSION,
    description=DESCRIPTION,
    author=AUTHOR,
    author_email=AUTHOR_EMAIL,
    url=URL,
    packages=find_packages(),
    keywords=KEYWORDS,
    license=LICENSE,
    include_package_data=True,
    package_data={"": ["*.tx"]},
    install_requires=["textx_ls_core"],
    entry_points={
        "textx_languages": [
            "model = dsl_project:model_language",
        ],
        "textx_generators": [
            "field_generator = dsl_project.generator:field_generator",
        ],
    },
    classifiers=[
        "Development Status :: 2 - Pre-Alpha",
        "Environment :: Console",
        "Intended Audience :: Developers",
        "Intended Audience :: Information Technology",
        "License :: Free For Educational Use",
        "Operating System :: OS Independent",
        "Programming Language :: Python :: 3 :: Only",
        "Programming Language :: Python :: 3.7",
        "Programming Language :: Python :: 3.8",
        "Programming Language :: Python :: 3.9",
        "Topic :: Software Development :: Libraries :: Python Modules",
    ],
)
