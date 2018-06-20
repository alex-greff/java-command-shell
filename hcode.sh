for file in $(find . -name '*.java'); do
    cat honorcode.txt $file > $file.tmp && mv $file.tmp $file
done

