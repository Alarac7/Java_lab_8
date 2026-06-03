# Laboratory Work No.8: Advanced Data Processing Architecture

## Overview
This repository contains the implementation of Laboratory Work No.8 for the Moscow Technical University of Communications and Informatics (MTUCI). 

The primary focus of this project is to build a highly scalable and decoupled data processing application by combining advanced Java mechanisms: meta-programming (Reflection API), custom annotations, modern Stream API, and asynchronous concurrent processing via the Concurrency Utilities. The architecture strictly complies with the Open/Closed Principle (OCP) from SOLID design guidelines.

## Architecture and Components

### 1. Custom Annotations (`@DataProcessor`)
* **Purpose**: Enforces a declarative metadata architecture by targeting data manipulation methods at runtime.
* **Retention Policy**: Retained in the JVM during execution via `@Retention(RetentionPolicy.RUNTIME)` to allow dynamic scanning by reflection mechanisms.

### 2. Core Orchestrator (`DataManager`)
An orchestration class responsible for managing the processing lifecycle through the following module methods:
* `registerDataProcessor(Object processor)`: Dynamically scans the provided class instance using the Reflection API to detect and index all methods marked with the `@DataProcessor` annotation.
* `loadData(String source)`: Handles robust file reading operations to load raw text lines into the processing state.
* `processData()`: Triggers the multi-threaded processing pipeline.
* `saveData(String destination)`: Safely exports the completely transformed dataset into an external target file.

### 3. Concurrency and Data Chunking
* **Parallel Execution**: To maximize processor core utilization, the dataset is adaptively segmented into distinct chunks based on available system CPU threads.
* **Thread Pooling**: Distributes chunk tasks asynchronously using an `ExecutorService` pool. Threads return targeted intermediate results via the `Callable` and `Future` interfaces, which are sequentially combined back into a singular dataset by the master thread using an `invokeAll()` blocking routine.
* **Stream API Integration**: Inside individual worker tasks, underlying data pipelines leverage functional streams for concise string transformations, grouping, and filtering.

## Technologies Used
* Language: Java
* Core Concepts: Reflection API (Meta-programming), Custom Annotations, Multithreading (`ExecutorService`, `Callable`, `Future`), Stream API, File I/O, Design Patterns (Orchestrator, Plugin-based Architecture), SOLID Principles (Open/Closed Principle).

## Key Learnings and Conclusion
This project successfully demonstrates the use of reflection and annotations to achieve low coupling and high extensibility in Java enterprise-like applications. By separating the execution core (`DataManager`) from individual business logic implementations (processors), the software layout allows developers to introduce new string modification filters seamlessly without refactoring or recompiling the multi-threaded orchestration subsystem.

---

## Author
* Name: Pavel Petrovich Koshelev
* Group: BST 2401
* Institution: MTUCI
* Year: 2026
