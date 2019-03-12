clf;
t= 0 : 0.01 : 3*pi;
y1 = 2 * sin(t);
y2 = 2 * sin(t-2);
plot(t, y1, 'r-'); % 빨간 실선
hold on;
plot(t, y2, 'b:'); % 파란 점선
