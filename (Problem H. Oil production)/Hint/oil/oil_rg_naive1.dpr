// Задача "Добыча нефти"
// Шестая Интернет-олимпиада сезона 2009-2010, усложненный уровень
// Автор задачи: Виталий Аксенов, aksenov@rain.ifmo.ru
// Автор решения: Рустам Ганеев, ganeev@rain.ifmo.ru

const
	MAX = 300;

var 
	a : array [1..MAX, 1..MAX] of longint;
	n, m, x1, y1, x2, y2, s : longint;

procedure finish;
begin
	close(input);
	close(output);
	halt(0);
end;
begin
	reset(input, 'oil.in');
	rewrite(output, 'oil.out');
	read(n, m, s);
	for x1 := 1 to n do begin
		for y1 := 1 to m do begin
			read(a[x1][y1]);
		end;
	end;
	for x1 := 1 to n - 1 do begin
		for y1 := 1 to m - 1 do begin
			for x2 := x1 + 1 to n do begin
				for y2 := y1 + 1 to m do begin
					if (a[x1][y1] + a[x1][y2] + a[x2][y1] + a[x2][y2] = s) then begin
						write(x1, ' ', y1, ' ', x2, ' ', y2);
						finish;
					end;
				end;
			end;
		end; 
	end;
	write(-1);
	finish;
end.
