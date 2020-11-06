# Aim

This tiny project allows to show constraints when dealing with `import` or `include` xsd tags.

The idea is to understand XML/XSD constraints to choose right model depdending of our developping needs...

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


# Real world application

## Context

In my case, I got a customer that imports xml data that respect several standards (about 100 XSDs files).

Of course, I'm using Jaxb to generate classes and it is really a pleasure (as ever).

I've made some Jaxb bindings to rename tags, attributes, etc... and it covers most of my uses.

***BUT*** some data structures in XSD are duplicated.

What can you do in this case? If you have the possibility to extract some XSD structures and put them into a common XSD, this is great!

In my case, the data structure is not changing a lot so modifying the standards XSD is acceptable.


## Steps to process

1. extract common data structures in one or several XSD files
2. when data structures are almost the same, use the XSD inheritance feature i.e. `extension`.
3. generate your new model
4. try to unmarshall... and...

***PROBLEM*** you have to prefix your new data structures with your new namespace prefix!

So do not forget to perform some preprocessing (e.g. with XSLT) to format properly the XML data.

By doing so, your customer still uses its standard XSD files and XML files.

The proper way indeed would be to modify the standard XSD but this is not always taken into account...

The main drawbacks are:

* XSD are modified so in case of update it can take take
* some preprocessing on XML is needed

The main advantage is that you don't have to duplicate your code...

If someone has found a solution without drawbacks, please let me know;)

I'm providing an example directly on this repository [TODO reference].


