for file in $(find ./src/commands -name '*.java'); do
    cat honorcode.txt $file > $file.tmp && mv $file.tmp $file
done

