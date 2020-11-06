# Aim

This tiny project allows to show constraints when dealing with `import` or `include` xsd tags.

# Presentation of the data model

The demonstration model is a `Library` which is a set of `Book` and `Author`.

Here is an xml example (without namespaces to avoid confusion):

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<library>
    <books>
        <book>
            <title>Misery</title>
            <author>Stephen King</author>
        </book>
    </books>
    <authors>
        <author>Stephen King</author>
    </authors>
</library>
~~~

The data model is described with two XSD schema files:

* `main.xsd` will describe the `Library` and embedded sequences
* `commons.xsd` will describe the `Book`, `Author` and `Title` structures

Depending of the naming convention, xsd and xml files will appear with suffixes describing the way `commons.xsd` has been "picked" by `main.xsd`: import or include.

# Constraints

By running tests, you will lend to the following constraints and conclusions.

## Import

By importing `commons.xsd` in `main.xsd`, you will see that:

* both files shall have a valid namespace
* you can use an imported complex structure (i.e. `Book`) by prefixing it with its namespace
* you can use an imported simple structure (i.e. `Author`) without prefixing it with its namespace

From a jaxb point of view, you will have two packages for each corresponding file/namespace.

## Include

By including `commons.xsd` in `main.xsd`, you will see that:

* `commons.xsd` shall be without namespace prefix: it will inherit it from `main.xsd`
* you can use an imported complex structure (i.e. `Book`) without prefixing it with its namespace
* you can use an imported simple structure (i.e. `Author`) without prefixing it with its namespace

From a jaxb point of view, you will have only one package for both files.

