/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package modena;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.SetChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

/**
 * Helper class for creating table views for testing
 */
public class SamplePageTableHelper {
    
    public static class Person {

        private BooleanProperty invited;
        private StringProperty firstName;
        private StringProperty lastName;
        private StringProperty email;
        
        private final String country = "New Zealand";

        public Person(String fName, String lName) {
            this(fName, lName, null);
        }

        public Person(String fName, String lName, String email) {
            this(fName, lName, email, false);
        }
        
        public Person(String fName, String lName, String email, boolean invited) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.invited = new SimpleBooleanProperty(invited);
            
            this.invited.addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    System.out.println(getFirstName() + " invited: " + t1);
                }
            });
        }
        
        public Boolean isInvited() { return invited.get(); }
        public BooleanProperty invitedProperty() { return invited; }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String firstName) {
            this.firstName.set(firstName);
        }

        public StringProperty firstNameProperty() {
            return firstName;
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String lastName) {
            this.lastName.set(lastName);
        }

        public StringProperty lastNameProperty() {
            return lastName;
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String email) {
            this.email.set(email);
        }

        public StringProperty emailProperty() {
            return email;
        }
        
        public String getCountry() {
            return country;
        }

        public String toString() {
            return "Person [ " + getFirstName() + " " + getLastName()/* + ", " + getEmail()*/ + " ]";
        }
    }

    private static ObservableList<Person> data = FXCollections.<Person>observableArrayList();
    
    static {
        // Data
        data.addAll(
            new Person("Jacob",     "Smith\nSmith\nSmith",    "jacob.smith<at>example.com", true ),
            new Person("Isabella",  "Johnson",  "isabella.johnson<at>example.com" ),
            new Person("Ethan",     "Williams", "ethan.williams<at>example.com", true ),
            new Person("Emma",      "Jones",    "emma.jones<at>example.com" ),
            new Person("Michael",   "Brown",    "michael.brown<at>example.com", true ),
            new Person("Olivia",    "Davis",    "olivia.davis<at>example.com" ),
            new Person("Alexander", "Miller",   "alexander.miller<at>example.com", true ),
            new Person("Sophia",    "Wilson",   "sophia.wilson<at>example.com" ),
            new Person("William",   "Moore",    "william.moore<at>example.com", true ),
            new Person("Ava",       "Taylor",   "ava.taylor<at>example.com" ),
            new Person("Joshua",    "Anderson", "joshua.anderson<at>example.com" ),
            new Person("Emily",     "Thomas",   "emily.thomas<at>example.com" ),
            new Person("Daniel",    "Jackson",  "daniel.jackson<at>example.com" ),
            new Person("Madison",   "White",    "madison.white<at>example.com" ),
            new Person("Jayden",    "Harris",   "jayden.harris<at>example.com" ),
            new Person("Abigail",   "Martin",   "abigail.martin<at>example.com" ),
            new Person("Noah",      "Thompson", "noah.thompson<at>example.com" ),
            new Person("Chloe",     "Garcia",   "chloe.garcia<at>example.com" ),
            new Person("Anthony",   "Martinez", "anthony.martinez<at>example.com" ),
            new Person("Mia",       "Robinson", "mia.robinson<at>example.com" ),
            new Person("Jacob",     "Smith" ),
            new Person("Isabella",  "Johnson" ),
            new Person("Ethan",     "Williams" ),
            new Person("Emma",      "Jones" ),
            new Person("Michael",   "Brown" ),
            new Person("Olivia",    "Davis" ),
            new Person("Alexander", "Miller" ),
            new Person("Sophia",    "Wilson" ),
            new Person("William",   "Moore" ),
            new Person("Ava",       "Taylor" ),
            new Person("Joshua",    "Anderson" ),
            new Person("Emily",     "Thomas" ),
            new Person("Daniel",    "Jackson" ),
            new Person("Madison",   "White" ),
            new Person("Jayden",    "Harris" ),
            new Person("Abigail",   "Martin" ),
            new Person("Noah",      "Thompson" ),
            new Person("Chloe",     "Garcia" ),
            new Person("Anthony",   "Martinez" ),
            new Person("Mia",       "Robinson" )
        );

    }
    
    static TableView createTableView(int width) {
        TableColumn<Person, String> firstNameCol, lastNameCol, nameCol, emailCol, countryCol;
        TableColumn<Person, Boolean> invitedCol;
        // Columns
        firstNameCol = new TableColumn<Person, String>();
        firstNameCol.setText("First");
//        Rectangle sortNode = new Rectangle(10, 10, Color.RED);
//        sortNode.fillProperty().bind(new ObjectBinding<Paint>() {
//            { bind(firstNameCol.sortTypeProperty()); }
//            @Override protected Paint computeValue() {
//                switch (firstNameCol.getSortType()) {
//                    case ASCENDING: return Color.GREEN;
//                    case DESCENDING: return Color.RED;
//                    default: return Color.BLACK;
//                }
//            }
//        });
//        firstNameCol.setSortNode(sortNode);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        firstNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override public void handle(TableColumn.CellEditEvent<Person, String> t) {
                System.out.println("Edit commit event: " + t.getNewValue());
            }
        });
        final Node graphic1 = new ImageView(new Image("file:src/helloworld/about_16.png"));
        lastNameCol = new TableColumn<Person, String>();
        lastNameCol.setGraphic(graphic1);
        lastNameCol.setText("Last");
        lastNameCol.setSortType(TableColumnBase.SortType.DESCENDING);
        lastNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Person, String> p) {
                return p.getValue().lastNameProperty();
            }
        });
        nameCol = new TableColumn<Person, String>();
        nameCol.setText("Name");
        nameCol.getColumns().addAll(firstNameCol, lastNameCol);
        emailCol = new TableColumn<Person, String>();
        emailCol.setText("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Person, String> p) {
                return p.getValue().emailProperty();
            }
        });
        countryCol = new TableColumn<Person, String>();
        countryCol.setText("Country");
        countryCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Person, String> p) {
                return new ReadOnlyObjectWrapper<String>("New Zealand");
            }
        });
        invitedCol = new TableColumn<Person, Boolean>();
        invitedCol.setText("Invited");
        invitedCol.setPrefWidth(55);
        invitedCol.setMaxWidth(55);
        invitedCol.setCellValueFactory(new PropertyValueFactory("invited"));
        invitedCol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {
            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) {
                return new CheckBoxTableCell<Person, Boolean>();
            }
        });
        
        
        TableView<Person> tableView = new TableView<Person>();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().setCellSelectionEnabled(false);
        tableView.setTableMenuButtonVisible(true);
        tableView.setItems(data);
        tableView.getColumns().addAll(invitedCol, nameCol, emailCol, countryCol);
        tableView.setPrefSize(width, 300);
        tableView.getSelectionModel().selectRange(2, 5);
        tableView.getSortOrder().addAll(firstNameCol,lastNameCol,emailCol,countryCol);
        return tableView;
    }
}