% For Prac08

t = 0 : 0.01 : 3*pi;
y1 = 2 * sin(t);
t2 = 2 * sin(t-2);

subplot(2, 1, 1);
plot(t, y1, 'r-');
title('y1(t) 그래프');
grid;

subplot(2, 1, 2);
plot(t, y2, 'b:');
title('y2(t) 그래프');
grid;
