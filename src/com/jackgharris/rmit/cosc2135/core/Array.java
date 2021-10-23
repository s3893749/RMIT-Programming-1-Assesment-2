package com.jackgharris.rmit.cosc2135.core;

//**** CUSTOM ARRAY CLASS ****\\
//This class implements easy to use arrays based on the the requirement to use only basic Java arrays for this assessment,
//this class adds and mimics similar features but not all features found in the Java Array lists and Hashmaps.
public class Array {

    //private Object type variable, is used to restrict the objects that can be added to this array
    private final Object objectType;
    //private Objects array called values, is used to store the values as objects inside this array
    private Object[] values;
    //private String keys array stores all the keys relating to values
    private String[] keys;
    //private integer count, stores the current count of entries within this array.
    private int count;
    //private integer for auto assigned keys, increases when entries are added to the array and are used as a key when no key is provided
    private int countPermanent;

    //**** ARRAY CONSTRUCTOR METHOD ****\\
    //this method is called when the array is created and accepts an object to use as the set object type for this array, arrays can only
    //store one type of object, for example, Controller or Strings, but not both.
    public Array(Object object){
        //set this arrays object type to the object that's been passed.
        this.objectType = object;
        //create the values and keys array ready for use, these are auto expanding so 0 is fine for now!
        this.values = new Object[0];
        this.keys = new String[0];
        //finally we set our count to 0 ready to be counted up when entries are added
        this.count = 0;
        //set the count permanent to 0 ready to be counted up as entries are added
        this.countPermanent = 0;
    }

    //**** ADD OBJECT METHOD ****\\
    //This method allows the calling method to add a object to the array, it accepts a value and a key parsed to it, if self as null the key
    //will default to its current position with in the array, This array add method will only accept objects or children of the object that
    //was specified in the constructor creation of this array.
    public Array add(Object value, String key){
        //step one, check if the provided value matches that of the class type assigned to this array, also check superclass
        //so we detected and accept children classes as well, if so proceed else throw an array storage exception.
        if(this.objectType.equals(value.getClass()) || this.objectType.equals(value.getClass().getSuperclass())){
            //now we have check that our value is matching the array type this is we check if we have been provided a key, if not then
                //Generate one by using the current index as a key
                if(key == null){
                    //if the key is null (not parsed), set the key to the current index count
                    key = String.valueOf(this.countPermanent);
                }
                //next check if we have room in our array to store this new entry, if so skip this, else run the expand Array private function
                if (this.count >= this.values.length) {
                    this.expandArray();
                }

                //finally now we have our correct key and validated our array size we can add the value and key to each array at
                //this current count.
                this.values[this.count] = value;
                this.keys[this.count] = key;
                //finally we increase the count ready for the next entry
                this.count++;
                this.countPermanent++;

        }else{
            //else as specified above if we provided the wrong object type for storage we throw an ArrayStorageException
            throw new ArrayStoreException();
        }
        return this;
    }

    //**** ARRAY KEY EXISTS METHOD ****\\
    //This method will accept a String key and check if we have a matching key in the array, if so we return true, else we return false.
    public boolean arrayKeyExists(String key) {
        //firstly we create our outcome, this is false by default and is return if no match is found, else if a match if found we set the outcome to true.
        boolean outcome = false;
        //next we create our i counter variable used to iterate over all the keys in this array
        int i = 0;
        //while our i counter is less then the total count of this array perform the check
        while (i < this.count) {
            //check if the contentEquals the value of the provided key (String)
            if (this.keys[i].contentEquals(key)) {
                //if so set the outcome to true.
                outcome = true;
            }
            //else we increase our i iterator and try the check the next value.
            i++;
        }
        //finally we return the outcome back to the caller
        return outcome;
    }

    //**** IN ARRAY METHOD ****\\
    //This method will accept a value and check if that exists in the array, Note we are not talking about keys here but the values themselves, returns true or false.
    public boolean inArray(Object value){
        //firstly we create a outcome variable and set it to false, this will be returned at the end of the check if no value in the array is found
        boolean outcome = false;
        //now we create our i variable to act as our counter
        int i = 0;
        //next we process our while loop to see if we have a value matching the value provided
        while(i < this.count){
            //if we have a match set the outcome to true, else leave it as false
            if(this.values[i].equals(value)){
                outcome = true;
            }
            //if we dont have a match on this value, increase our i counter and try checking the next integration
            i++;
        }
        //else finally we return the outcome of the inArray check.
        return outcome;
    }

    //**** GET VALUE METHOD ****\\
    //This public method accepts a key in the form of a string and if have a value matching that key in the array we will return it, else we return false.
    public Object getValue(String key){
        //create outcome to return to the requester, this outcome is false by default as if no key match is found false will be returned.
        Object outcome = false;
        //now create our i counter to loop over the keys in the key array using a while loop
        int i = 0;
        //perform the while loop whilst our i counter is less than the total count of values in this array
        while(i < this.count){
            //next we check if this current iteration has a key match, if so set the outcome to the matching value, else keep iterating.
            if(this.keys[i].contentEquals(key)){
                //match found! now we have found a match then set the return outcome to the matching value for the index of this key.
                outcome = this.values[i];
            }
            //else if we dont find a match increase the counter and try the next iteration
            i++;
        }
        //finally once all keys have been checked return the outcome
        return outcome;
    }

    //**** GET ALL VALUES GETTER METHOD ****\\
    //returns all the values in the array as a object
    public Object[] getValues(){
        return this.values;
    }

    //**** GET ALL KEYS GETTER METHOD ****\\
    //returns all the keys in an array of strings
    public String[] getKeys(){
        return this.keys;
    }


    //**** LENGTH GETTER METHOD ****\\
    //The length getter method can be called publicly and is used to return the current length of the array (this.count)
    public int length(){
        //return the current count
        return this.count;
    }

    //**** EXPAND ARRAY METHOD ****\\
    //This method will automatically expand our array by the value of 10 each time it is called, this method is private and can only be
    //accessed from this class its self, its primary caller is the public add method that will check if our count is too high and if so then
    //call this method to process the expansion of the array before proceeding.
    private void expandArray(){

        //create our new arrays that are the size of our current array +10
        //create new Objects array to store values
        Object[] newValuesArray = new Object[this.values.length+1];
        //create new String array to store keys
        String[] newKeysArray = new String[this.keys.length+1];

        //next we create our integer counter
        int i = 0;
        //now we create a while loop and copy all the values from our original arrays into our two new expanded arrays
        while(i < this.count){
            //copy Values array at the current index iteration
            newValuesArray[i] = this.values[i];
            //copy the keys array at the current iteration
            newKeysArray[i] = this.keys[i];
            //now increase our counter and process the next iteration until we reach the current values.length number
            i++;
        }
        //finally we set our old values array to our new one and our old keys array to our new keys array
        this.values = newValuesArray;
        this.keys = newKeysArray;
    }

    //**** DELETE ARRAY METHOD ****\\
    //Delete value method, takes a key String as input and checks if the key exists in the array, if so it will create tow new arrays and
    //exclude the key from them, causing it to be deleted, this applies to both the key array and the values array
    public void delete(String key){

        //step one check if this is a valid key, if not skip this code
        if(this.arrayKeyExists(key)) {
            //next if we have a valid key to delete we need to create two new arrays to transfer all the values we are not deleting too.
            //these need to be the same length as the main array -1 to account for the deleted value
            Object[] newValues = new Object[this.values.length-1];
            String[] newKeys = new String[this.keys.length-1];

            //next we create two separate counters to track the count of each array, the new and the old
            // i represents the old array number
            int i  = 0;
            // ii represents the new array number
            int ii = 0;
            //now we use our main original array counter to count over the main array keys
            while(i < this.count){
                //if the key does not match the key that we have provided for deletion then add them to the new array
                if(!this.keys[i].matches(key)){
                    //set the newKeys value to the old keys array for that index
                    newKeys[ii] = this.keys[i];
                    //set the newValues to the old values array value for that index
                    newValues[ii] = this.values[i];
                    //finally increase our new keys counter
                    ii++;
                }
                //after we have performed the first check increase our counter and keep going until we have checked all our entries.
                i++;
            }
            //finally we remove one from the array wide count and set the main arrays to the value of our new arrays with the value removed
            this.count --;
            //set the main values to our new values
            this.values = newValues;
            //set the main keys to our new keys
            this.keys = newKeys;
        }
    }

}
