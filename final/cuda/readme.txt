COMPILE:

nvcc red.cu `pkg-config --cflags --libs opencv` -o MotherRussia

RUN

./MotherRussia [source_image]
